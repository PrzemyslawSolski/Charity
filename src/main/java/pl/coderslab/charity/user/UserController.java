package pl.coderslab.charity.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.email.EmailService;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final UserUtil userUtil;

    @Autowired
    public UserController(UserService userService, EmailService emailService, UserUtil userUtil) {
        this.userService = userService;
        this.emailService = emailService;
        this.userUtil = userUtil;
    }

    @GetMapping("/register")
    public String registerAction(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
//    @ResponseBody
    public String registerAction(@RequestParam String password2,
                                 Model model,
                                 @Validated({RegistrationValidationGroup.class}) @ModelAttribute User user,
                                 BindingResult result) {
        if (!user.getPassword().equals(password2)) {
            result.rejectValue("password", "error.user", "Błędnie powtórzone hasło");
        }
        if(!userUtil.isPasswordOk(password2)){
            result.rejectValue("password", "error.user", "Hasło musi zawierać conajmniej 8 znaków, małą i wielką literę, cyfrę oraz znak specjalny #?!@$%^&*-");
        }
        if (result.hasErrors()) {
            return "register";
        }
        User existingUser = userService.getFirstByEmail(user.getEmail().toLowerCase());
        if (existingUser != null) {
            result.addError(new FieldError("user", "email",
                    "Błędny email lub użytkownik istnieje"));
            return "register";
        }
        user.setEmail(user.getEmail().toLowerCase());
        user.setPasswordHash(user.getPassword());
//        Hibernate.initialize(user.getEvents());
        userService.save(user);
        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginAction(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginAction(@Validated({LoginValidationGroup.class}) User user,
                              BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }

        User existingUser = userService.getFirstByEmail(user.getEmail().toLowerCase());
        if (existingUser == null ||
                !BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {
            result.addError(new FieldError("user", "password",
                    "Niepoprawny email lub hasło"));
            return "login";
        }
        session.setAttribute("userId", existingUser.getId());
        session.setAttribute("firstName", existingUser.getName());
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logoutAction(HttpSession session, HttpServletRequest request) {
        session.invalidate();
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/remind")
    public String remindPasswordAction(HttpSession session) {
        userUtil.clearSessionUserData(session);
        return "remind";
    }

    @PostMapping("/remind")
//    @ResponseBody
    public String remindPasswordPostAction(@RequestParam String email,
                                           Model model,
                                           HttpSession session) {
        userUtil.clearSessionUserData(session);
        User user = userService.getFirstByEmail(email.toLowerCase());
        String messageText = "";
        if (user != null) {
            userService.generateSaveSendToken(user);
//            String token = userUtil.generateToken(32);
//
//            messageText = "W aplikacji 'Zacznij pomagać' wybrano opcję zmiany zapomnianego hasła. "
//                    + System.getProperty("line.separator")
//                    + "Jeżeli to nie Ty, nie rób nic."
//                    + System.getProperty("line.separator")
//                    + System.getProperty("line.separator")
//                    + "Jeżeli wybrałeś opcję zmiany hasła, kliknij poniższy link:"
//                    + System.getProperty("line.separator")
//                    + "http://" + "localhost:8080/remind/" + token;
////        emailService.sendSimpleMessage(email, "Zmiana hasła", messageText);
////        emailService.sendSimpleMessage("psolski@poczta.onet.pl", "Zmiana hasła", messageText);
        }
        List<String> messages = new ArrayList();
        messages.add("Na adres: " + email);
        messages.add("został wysłany link do zmiany hasła.");
        messages.add("Nie zapomnij sprawdzić folderu spam w Twojej poczcie.");
        model.addAttribute("messages", messages);
        return "confirmation";
    }

    @GetMapping("remind/{token}")
//    @ResponseBody
    public String resetPasswordFromToken(@PathVariable String token,
                                         Model model,
                                         HttpSession session) {
        userUtil.clearSessionUserData(session);
        User user = userService.getFirstByToken(token);
        String message = "";
        if (user == null) {
            List<String> messages = new ArrayList();
            messages.add("Oooops!");
            messages.add("coś poszło nie tak...");
            messages.add("Sprawdź link i spróbuj jeszcze raz.");
            model.addAttribute("messages", messages);
            return "confirmation";
        }
//            if(user.getTokenValidityDay().compareTo(LocalDate.now())>=0
//                && user.getTokenValidityTime().compareTo(LocalTime.now())>=0){
//        if (user.getTokenValidity().compareTo(new Timestamp(System.currentTimeMillis())) >= 0) {
        if (userUtil.isTokenValid(user)) {
            //token ok and valid
            message = "token ok";
            user.setPassword("");
            model.addAttribute("user", user);
            return "pass_change";

        } else {
            //token correct but no longer valid
            message = "token ok, but invalid";
            List<String> messages = new ArrayList();
            messages.add("Oooops!");
            messages.add("Twój link stracił ważność.");
            messages.add("Spróbuj jeszcze raz.");
            model.addAttribute("messages", messages);
            return "confirmation";
        }
    }

    @PostMapping("remind/{token}")
    public String resetPasswordFromToken(@PathVariable String token,
                                         @RequestParam String password2,
                                         @ModelAttribute User user,
                                         BindingResult result,
                                         Model model,
                                         HttpSession session) {
        userUtil.clearSessionUserData(session);
        if (!user.getPassword().equals(password2)) {
            result.rejectValue("password", "error.user", "Błędnie powtórzone hasło");
        }
        if(!userUtil.isPasswordOk(password2)){
            result.rejectValue("password", "error.user", "Hasło musi zawierać conajmniej 8 znaków, małą i wielką literę, cyfrę oraz znak specjalny #?!@$%^&*-");
        }
        if (result.hasErrors()) {
            return "pass_change";
        }
        User existingUser = userService.getFirstByToken(token);
        if (existingUser != null
                && userUtil.isTokenValid(existingUser)
        ) {
            existingUser.setPasswordHash(password2);
            userService.save(existingUser);
            List<String> messages = new ArrayList();
            messages.add("Hasło zostało zmienione.");
            messages.add("Teraz możesz przejść do strony logowania");
            messages.add("i zalogować się nowym hasłem.");
            model.addAttribute("messages", messages);
            return "confirmation";
        }
        List<String> messages = new ArrayList();
        messages.add("Ooops!");
        messages.add("Masz błędny lub nieaktualny link.");
        messages.add("Sprawdź i spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);

        return "confirmation";
    }

}
