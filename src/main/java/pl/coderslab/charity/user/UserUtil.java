package pl.coderslab.charity.user;

import org.springframework.stereotype.Component;

import java.util.Arrays;

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
}
