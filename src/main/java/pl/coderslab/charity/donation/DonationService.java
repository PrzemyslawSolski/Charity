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
