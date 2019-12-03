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
import java.time.LocalDate;
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

    public boolean isInstitutionIdOk (Long id){
        if(institutionService.getOne(id)==null){
            return false;
        }
        return true;
    }

    public int numberOfSelectedCategories(CategoryContainer categoryContainer) {
        return (int) categoryContainer.getCategories().stream().filter(Category::isChosen).count();
    }
    public int numberOfSelectedCategories(List<Category> categories) {
        return categories.stream().filter(Category::isChosen).collect(Collectors.toList()).size();
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

    public long[] getSelectedCategoriesIds(List<Category> categories) {
        int noOfSelectedCategories = numberOfSelectedCategories(categories);
//        List<Category> categories = categoryContainer.getCategories();
        long[] selectedCategoriesIds = new long[noOfSelectedCategories];
        int counter = 0;
        for (Category category : categories.stream().filter(Category::isChosen).collect(Collectors.toList())) {
            selectedCategoriesIds[counter] = category.getId();
            counter++;
        }
        return selectedCategoriesIds;
    }

    public Donation collectDonationFromSession(HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        if(donation==null){
            donation = new Donation();
        }
        if(session.getAttribute("quantity")!=null) {
            donation.setQuantity((Integer) session.getAttribute("quantity"));
        }
        if(session.getAttribute("institutionId")!=null) {
            donation.setInstitution(institutionService.getOne((Long) session.getAttribute("institutionId")));
        }
        if(categoryService.getCategoriesFromSession(session)!=null) {
            donation.setCategories(categoryService.getCategoriesFromSession(session));
        }
        return donation;
    }

    /**
     *
     * @param donation
     * @return
     */
    public String getFirstFormWithWrongData(Donation donation){
        if (donation.getCategories() == null || donation.getCategories().isEmpty()) {
            return "donate";
        }
        if (donation.getQuantity() < 1) {
            return "quantity";
        }
        if (donation.getInstitution() == null) {
            return "institution";
        }
        if(!isDeliveryCorrect(donation)){
            return "address";
        }
        return null;
    }

    public void clearSessionDeliveryData(HttpSession session) {
        session.removeAttribute("donation");
        session.removeAttribute("quantity");
        session.removeAttribute("institutionId");
        session.removeAttribute("categoriesIds");
    }

    public boolean isDeliveryCorrect(Donation donation) {
        if (
            donation.getCity() == null || donation.getCity().isEmpty()
            || donation.getPhoneNumber() == null || donation.getPhoneNumber().isEmpty()
            || donation.getPickUpDate() == null || donation.getPickUpDate().isBefore(LocalDate.now().plusDays(1))
            || donation.getPickUpTime() == null
            || donation.getStreet() == null || donation.getStreet().isEmpty()
            || donation.getZipCode() == null
        ) {
            return false;
        }
        return true;
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
