package pl.coderslab.charity.user;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);
    private static final String EMAIL_REQUIREMENTS =
            "Hasło musi zawierać conajmniej 8 znaków, małą i wielką literę, cyfrę oraz znak specjalny #?!@$%^&*-";
    private static final String PASSWORDS_NOT_SAME = "Błędnie powtórzone hasło";

    private final UserService userService;
    private final UserUtil userUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, UserUtil userUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userUtil = userUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/register")
    public String registerAction(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerAction(@RequestParam String password2,
                                 Model model,
                                 @Validated({RegistrationValidationGroup.class}) @ModelAttribute User user,
                                 BindingResult result) {
        if (!user.getPassword().equals(password2)) {
            result.rejectValue("password", "error.user", PASSWORDS_NOT_SAME);
        }
        if (!userUtil.isPasswordOk(password2)) {
            result.rejectValue("password", "error.user", EMAIL_REQUIREMENTS);
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
//        user.setPasswordHash(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        logger.info("User: " + user.getEmail() + " registered");
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
            logger.info("User: " + user.getEmail() + " entered wrong password");
            return "login";
        }
        session.setAttribute("userId", existingUser.getId());
        session.setAttribute("firstName", existingUser.getName());
        logger.info("User: " + existingUser.getEmail() + " logged in");
        return "redirect:/";
    }

    @GetMapping("/loginok")
    public String loginOkAction(HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(user!=null){
            session.setAttribute("userId", user.getId());
            session.setAttribute("firstName", user.getName());
            logger.info("User: " + user.getEmail() + " logged in");
        }
        return "redirect:donate";
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
    public String remindPasswordPostAction(@RequestParam String email,
                                           Model model,
                                           HttpSession session) {
        userUtil.clearSessionUserData(session);
        User user = userService.getFirstByEmail(email.toLowerCase());
        if (user != null) {
            userService.generateSaveSendToken(user);
        }
        userUtil.setTokenSentMessage(model, email);
        logger.info("User: " + email + " requested password reminder");
        return "confirmation";
    }

    @GetMapping("remind/{token}")
    public String resetPasswordFromToken(@PathVariable String token,
                                         Model model,
                                         HttpSession session) {
        userUtil.clearSessionUserData(session);
        User user = userService.getFirstByToken(token);
        if (user == null) {
            userUtil.setWrongTokenMessage(model);
            logger.info("Entered incorrect token: " + token);
            return "confirmation";
        }
        if (userUtil.isTokenValid(user)) {
            //token ok and valid
            user.setPassword("");
            model.addAttribute("user", user);
            return "pass_change";

        } else {
            //token correct but no longer valid
            userUtil.setNoLongerValidTokenMessage(model);
            logger.info("Used invalidated token: " + token);
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
            result.rejectValue("password", "error.user", PASSWORDS_NOT_SAME);
        }
        if (!userUtil.isPasswordOk(password2)) {
            result.rejectValue("password", "error.user", EMAIL_REQUIREMENTS);
        }
        if (result.hasErrors()) {
            return "pass_change";
        }
        User existingUser = userService.getFirstByToken(token);
        if (existingUser != null
                && userUtil.isTokenValid(existingUser)
        ) {
//            existingUser.setPasswordHash(password2);
            existingUser.setPassword(bCryptPasswordEncoder.encode(password2));
            userService.save(existingUser);
            userUtil.setPassChangedMessage(model);
            logger.info("User: " + existingUser.getEmail() + " changed password by reminder");
            return "confirmation";
        }
        userUtil.setWrongOrInvalidTokenMessage(model);
        logger.info("Hijacking attempt. Wrong token illegally posted");
        return "confirmation";
    }

    @PostMapping("/perform_log")
    @ResponseBody
    public String preformLogin(){
        return "jestem";
    }

}
