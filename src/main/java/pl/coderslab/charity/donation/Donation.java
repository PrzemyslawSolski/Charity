package pl.coderslab.charity.donation;

import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private int quantity;
    @ManyToMany
    @JoinTable(name = "donation_categories",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @NotEmpty
    private List<Category> categories = new ArrayList<>();
    @NotNull
    @ManyToOne
    private Institution institution;
    @NotBlank(groups={DeliveryValidationGroup.class})
    private String street;
    @NotBlank(groups={DeliveryValidationGroup.class})
    private String city;
    @NotBlank(groups={DeliveryValidationGroup.class})
    private String zipCode;
    @NotBlank(groups={DeliveryValidationGroup.class})
    private String phoneNumber;
    @NotNull(groups={DeliveryValidationGroup.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate pickUpDate;
    @NotNull(groups={DeliveryValidationGroup.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime pickUpTime;
    private String pickUpComment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(LocalTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getPickUpComment() {
        return pickUpComment;
    }

    public void setPickUpComment(String pickUpComment) {
        this.pickUpComment = pickUpComment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
