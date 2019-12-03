package pl.coderslab.charity.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtil {

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

    public boolean isTokenValid(User user){
        return user.getTokenValidity().compareTo(new Timestamp(System.currentTimeMillis())) >= 0;
    }

    public boolean isPasswordOk(String password) {
        return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
    }

    public void setTokenSentMessage(Model model, String email){
        List<String> messages = new ArrayList();
        messages.add("Na adres: " + email);
        messages.add("został wysłany link do zmiany hasła.");
        messages.add("Nie zapomnij sprawdzić folderu spam w Twojej poczcie.");
        model.addAttribute("messages", messages);
    }

    public void setWrongTokenMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Oooops!");
        messages.add("coś poszło nie tak...");
        messages.add("Sprawdź link i spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);
    }

    public void setNoLongerValidTokenMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Oooops!");
        messages.add("Twój link stracił ważność.");
        messages.add("Spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);
    }

    public void setPassChangedMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Hasło zostało zmienione.");
        messages.add("Teraz możesz przejść do strony logowania");
        messages.add("i zalogować się nowym hasłem.");
        model.addAttribute("messages", messages);
    }

    public void setWrongOrInvalidTokenMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Ooops!");
        messages.add("Masz błędny lub nieaktualny link.");
        messages.add("Sprawdź i spróbuj jeszcze raz.");
        model.addAttribute("messages", messages);
    }

    public void clearSessionUserData(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("firstName");
    }
}
