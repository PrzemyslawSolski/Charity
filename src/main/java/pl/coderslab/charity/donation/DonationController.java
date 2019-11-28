package pl.coderslab.charity.donation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryContainer;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DonationController {
    private final CategoryService categoryService;
    private final DonationService donationService;
    private final InstitutionService institutionService;

    public DonationController(CategoryService categoryService, DonationService donationService, InstitutionService institutionService) {
        this.categoryService = categoryService;
        this.donationService = donationService;
        this.institutionService = institutionService;
    }

    @GetMapping("/donate")
    public String donateAction(Model model) {
        CategoryContainer categoryContainer = new CategoryContainer();
        categoryContainer.setCategories(categoryService.findAll());
        model.addAttribute("categoryContainer", categoryContainer);
        return "categories";
    }

    @PostMapping("/donate")
    public String donateAction(Model model, HttpSession session,
                               @ModelAttribute CategoryContainer categoryContainer,
                               BindingResult result) {
        if (donationService.numberOfSelectedCategories(categoryContainer) == 0) {
            result.addError(new FieldError("categoryContainer", "categories",
                    "Musisz wybrać conajmniej jedną pozycję"));
        }
        if (result.hasErrors()) {
            return "categories";
        }
        session.setAttribute("categoriesIds", donationService.getSelectedCategoriesIds(categoryContainer));
        return "redirect:quantity";
    }

    @GetMapping("/quantity")
    public String quantityAction(Model model) {
        int quantity = 0;
        model.addAttribute("quantity", quantity);
        return "quantity";
    }

    @PostMapping("/quantity")
    public String quantityAction(@RequestParam Integer quantity, HttpSession session) {
        session.setAttribute("quantity", quantity);
        return "redirect:institution";
    }

    @GetMapping("/institution")
    public String organizationAction(Model model) {
        model.addAttribute("institutions", institutionService.findAll());
        return "institution";
    }

    @PostMapping("/institution")
    public String organizationAction(@RequestParam Long institution, HttpSession session) {
        session.setAttribute("institutionId", institution);
        return "redirect:address";
    }

    @GetMapping("/address")
    public String addressAction(Model model) {
        model.addAttribute("donation", new Donation());
        return "address";
    }

    @PostMapping("/address")
    public String addressAction(Model model, HttpSession session, @ModelAttribute Donation donation, BindingResult result) {
        if (result.hasErrors()) {
            return "address";
        }
        session.setAttribute("donation", donation);
        return "redirect:summary";
    }

    @GetMapping("/summary")
    public String summaryAction(Model model, HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        donation.setQuantity((Integer) session.getAttribute("quantity"));
        donation.setInstitution(institutionService.getOne((Long) session.getAttribute("institutionId")));
//        List<Category> categories = new ArrayList<>();
////        long [] categoriesIds =  (long []) session.getAttribute("categoriesIds");
//
//        for (long categoryId : (long[]) session.getAttribute("categoriesIds")) {
//            categories.add(categoryService.getOne(categoryId));
//        }
        donation.setCategories(categoryService.getCategoriesFromSession(session));
        model.addAttribute("donation", donation);
        return "summary";
    }

    @PostMapping("/summary")
    public String summaryAction(Model model, HttpSession session, @ModelAttribute Donation donation, BindingResult result) {
//        Donation dd = donation;
//        donation.setCategories(categoryService.getCategoriesFromSession(session));
        return "redirect:donate";
    }


}
