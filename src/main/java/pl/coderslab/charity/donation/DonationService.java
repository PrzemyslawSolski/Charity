package pl.coderslab.charity.donation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryContainer;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final InstitutionService institutionService;
    private final CategoryService categoryService;

    @Autowired
    public DonationService(DonationRepository donationRepository, InstitutionService institutionService, CategoryService categoryService) {
        this.donationRepository = donationRepository;
        this.institutionService = institutionService;
        this.categoryService = categoryService;
    }

    public int numberOfSelectedCategories(CategoryContainer categoryContainer) {
        return categoryContainer.getCategories().stream().filter(Category::isChosen).collect(Collectors.toList()).size();
    }

    public long[] getSelectedCategoriesIds(CategoryContainer categoryContainer) {
        int noOfSelectedCategories = numberOfSelectedCategories(categoryContainer);
//        List<Category> categories = categoryContainer.getCategories();
        long[] selectedCategoriesIds = new long[noOfSelectedCategories];
        int counter = 0;
        for (Category category : categoryContainer.getCategories().stream().filter(Category::isChosen).collect(Collectors.toList())) {
            selectedCategoriesIds[counter] = category.getId();
            counter++;
        }
        return selectedCategoriesIds;
    }

    public Donation collectDonationFromSession(HttpSession session){
        Donation donation = (Donation) session.getAttribute("donation");
        donation.setQuantity((Integer) session.getAttribute("quantity"));
        donation.setInstitution(institutionService.getOne((Long) session.getAttribute("institutionId")));
        donation.setCategories(categoryService.getCategoriesFromSession(session));
        return donation;
    }

    public long getTotalQuantity() {
        return donationRepository.getTotalQuantity();
    }

    public long getNumberOfDonatedInstitutions() {
        return donationRepository.getDonatedInstitutions().size();
    }

    public void save(Donation donation) {
        donationRepository.save(donation);
    }

    public Donation getOne(long id) {
        return donationRepository.getOne(id);
    }

    public Donation getOneWithCategories(long id) {
        Donation donation = getOne(id);
        Hibernate.initialize(donation.getCategories());
        return donation;
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public void deleteById(long id) {
        donationRepository.deleteById(id);
    }

}
