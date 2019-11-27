package pl.coderslab.charity.donation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryContainer;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public int numberOfSelectedCategories(CategoryContainer categoryContainer){
        return categoryContainer.getCategories().stream().filter(Category::isChosen).collect(Collectors.toList()).size();
    }

    public long getTotalQuantity(){
        return donationRepository.getTotalQuantity();
    }

    public long getNumberOfDonatedInstitutions(){
        return donationRepository.getDonatedInstitutions().size();
    }

    public void save(Donation donation){
        donationRepository.save(donation);
    }

    public Donation getOne(long id){
        return donationRepository.getOne(id);
    }

    public Donation getOneWithCategories(long id){
        Donation donation = getOne(id);
        Hibernate.initialize(donation.getCategories());
        return donation;
    }

    public List<Donation> findAll(){
        return donationRepository.findAll();
    }

    public void deleteById(long id){
        donationRepository.deleteById(id);
    }

}
