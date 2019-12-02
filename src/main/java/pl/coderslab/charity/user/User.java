package pl.coderslab.charity.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
}
