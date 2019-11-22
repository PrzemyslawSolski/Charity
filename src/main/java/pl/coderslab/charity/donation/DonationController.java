package pl.coderslab.charity.donation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.category.CategoryService;

@Controller
public class DonationController {
    private final CategoryService categoryService;

    public DonationController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/donate")
    public String donateAction(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "form";
    }
}
