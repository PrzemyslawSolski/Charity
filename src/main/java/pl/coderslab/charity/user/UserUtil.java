package pl.coderslab.charity.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtil {
    /**
     * Generates token of random characters of a defined length
     * @param tokenLength length of a token to be generated
     * @return String of random characters
     */
    public String generateToken(int tokenLength) {
        StringBuilder token = new StringBuilder();
        int code;
        do {
            code = (int) (1.0 * 93 * Math.random()) + 33;
            if ((code >= '0' && code <= '9')
                    || (code >= 'A' && code <= 'Z')
                    || (code >= 'a' && code <= 'z')
            ) {
                token.append((char)code);
            }
        } while (token.length() < tokenLength);
        return token.toString();
    }

    /**
     * Checks validity of a token, i.e. compares current time with token validity
     * stored in db
     * @param user
     * @return true if token valid, false if invalid
     */
    public boolean isTokenValid(User user){
        return user.getTokenValidity().compareTo(new Timestamp(System.currentTimeMillis())) >= 0;
    }


    /**
     * Checks wether a password meets requirements:
     * at least: 8 characters, 1 capital letter, 1 small lettet, 1 digit, 1 special character
     * @param password
     * @return true if pass meets the requirements; false - if it's not
     */
    public boolean isPasswordOk(String password) {
        return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
    }


    /**
     * Sets in model a message to be presented once email to user has been sent
     * @param model
     * @param email - email of user requested pass reminder link to be sent
     */
    public void setTokenSentMessage(Model model, String email){
        List<String> messages = new ArrayList();
        messages.add("Na adres: " + email);
        messages.add("został wysłany link do zmiany hasła.");
        messages.add("Nie zapomnij sprawdzić folderu spam w Twojej poczcie.");
        model.addAttribute("messages", messages);
    }

    /**
     * Sets in model a message to be presented once the wrong token (not existing in db) has been entered
     * as a requirement for changing password
     * @param model
     */
    public void setWrongTokenMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Oooops!");
        messages.add("coś poszło nie tak...");
        messages.add("Sprawdź link i spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);
    }

    /**
     * Sets a message to be presented in case entered token exists in db, but is no longer valid
     * @param model
     */
    public void setNoLongerValidTokenMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Oooops!");
        messages.add("Twój link stracił ważność.");
        messages.add("Spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);
    }

    /**
     * Sets a message to be presented once a pass has been changed
     * @param model
     */
    public void setPassChangedMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Hasło zostało zmienione.");
        messages.add("Teraz możesz przejść do strony logowania");
        messages.add("i zalogować się nowym hasłem.");
        model.addAttribute("messages", messages);
    }

    /**
     * Sets a message to be presented once the entered token is either wrong or no longer valid
     * @param model
     */
    public void setWrongOrInvalidTokenMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Ooops!");
        messages.add("Masz błędny lub nieaktualny link.");
        messages.add("Sprawdź i spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);
    }

    /**
     * Clears session from user related data.
     * Method used when user requests pass reminder or has entered a path for pass reminded change
     * @param session
     */
    public void clearSessionUserData(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("firstName");
    }
}
