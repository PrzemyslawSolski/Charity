package pl.coderslab.charity.user;

import javax.persistence.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.charity.role.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(groups={RegistrationValidationGroup.class})
    private String name;
    @NotEmpty(groups={RegistrationValidationGroup.class})
    private String surname;
    @NotEmpty(groups={RegistrationValidationGroup.class, LoginValidationGroup.class})
    @Email(groups={RegistrationValidationGroup.class, LoginValidationGroup.class})
    private String email;
    @NotEmpty(groups={RegistrationValidationGroup.class, LoginValidationGroup.class})
    private String password;
    private int active;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role"
//            ,
//            joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Role> roles;
    private String token;
    private Timestamp tokenValidity;


    public void setPasswordHash(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
//        this.password = BCryptPasswordEncoder.encode(password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Timestamp getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(Timestamp tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
