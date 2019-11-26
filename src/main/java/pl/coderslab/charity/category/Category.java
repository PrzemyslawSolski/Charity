package pl.coderslab.charity.category;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Transient
    private boolean chosen;
//    @ManyToMany(mappedBy = "categories")
//    private List<Donation> donations = new ArrayList<>();

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

//    public List<Donation> getDonations() {
//        return donations;
//    }
//
//    public void setDonations(List<Donation> donations) {
//        this.donations = donations;
//    }
}
