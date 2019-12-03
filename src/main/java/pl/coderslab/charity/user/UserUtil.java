package pl.coderslab.charity.user;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

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

    public void clearSessionUserData(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("firstName");
    }
}
