package pl.coderslab.charity.donation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryContainer;
import pl.coderslab.charity.category.CategoryService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DonationController {
    private final CategoryService categoryService;
    private final DonationService donationService;

    public DonationController(CategoryService categoryService, DonationService donationService) {
        this.categoryService = categoryService;
        this.donationService = donationService;
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


}
