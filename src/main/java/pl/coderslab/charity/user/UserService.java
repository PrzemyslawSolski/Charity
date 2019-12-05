package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.CharityApplication;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.role.Role;
import pl.coderslab.charity.role.RoleRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, UserUtil userUtil, RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    public void generateSaveSendToken(User user) {
        String token = userUtil.generateToken(32);
        user.setToken(token);
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
        if (CharityApplication.SEND_MAIL) {
//        emailService.sendSimpleMessage(user.getEmail(), "Zmiana hasła", messageText);
            emailService.sendSimpleMessage("psolski@poczta.onet.pl", "Zmiana hasła", messageText);
        }
    }


    public User getFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email.toLowerCase());
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }

    public User getFirstByToken(String token) {
        return userRepository.findFirstByToken(token);
    }

    public void save(User user) {
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        if (user.getRoles() == null) {
//            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            user.setRoles(new HashSet<Role>());
        }
        user.getRoles().add(userRole);
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
