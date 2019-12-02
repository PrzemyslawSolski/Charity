package pl.coderslab.charity.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
//    @ResponseBody
    public String register(@RequestParam String password2,
                           Model model,
                           @Validated({RegistrationValidationGroup.class}) @ModelAttribute User user,
                           BindingResult result) {
        if (!user.getPassword().equals(password2)) {
            result.rejectValue("password", "error.user", "Błędnie powtórzone hasło");
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
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated({LoginValidationGroup.class}) User user,
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
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
