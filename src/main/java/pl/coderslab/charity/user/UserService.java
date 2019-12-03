package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.CharityApplication;
import pl.coderslab.charity.email.EmailService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, UserUtil userUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.emailService = emailService;
    }

    public void generateSaveSendToken(User user){
        String token = userUtil.generateToken(32);
        user.setToken(token);
//        user.setTokenValidityDay(LocalDateTime.now().plusHours(1).toLocalDate());
//        user.setTokenValidityTime(LocalTime.now().plusHours(1));
        user.setTokenValidity(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
        save(user);

        String messageText = "W aplikacji 'Zacznij pomagać' wybrano opcję zmiany zapomnianego hasła. "
                + System.getProperty("line.separator")
                + "Jeżeli to nie Ty, nie rób nic."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Jeżeli wybrałeś opcję zmiany hasła, kliknij poniższy link:"
                + System.getProperty("line.separator")
                + "http://" + "localhost:8080/remind/" + token;
        if(CharityApplication.SEND_MAIL) {
//        emailService.sendSimpleMessage(user.getEmail(), "Zmiana hasła", messageText);
        emailService.sendSimpleMessage("psolski@poczta.onet.pl", "Zmiana hasła", messageText);
        }
    }

    public User getFirstByEmail(String email){
        return userRepository.findFirstByEmail(email.toLowerCase());
    }

    public User getFirstByToken(String token){
        return userRepository.findFirstByToken(token);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getOne(long id) {
        return userRepository.getOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

}
