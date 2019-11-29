package pl.coderslab.charity.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.category.CategoryContainer;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.institution.InstitutionService;

import javax.servlet.http.HttpSession;

@Controller
public class DonationController {
    private final CategoryService categoryService;
    private final DonationService donationService;
    private final InstitutionService institutionService;
    private final EmailService emailService;

    @Autowired
    public DonationController(CategoryService categoryService, DonationService donationService, InstitutionService institutionService, EmailService emailService) {
        this.categoryService = categoryService;
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.emailService = emailService;
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
//        int quantity = 0;
//        model.addAttribute("quantity", quantity);
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
        model.addAttribute("donation", donationService.collectDonationFromSession(session));
        return "summary";
    }

    @PostMapping("/summary")
    public String summaryAction(Model model, HttpSession session, @ModelAttribute Donation donation, BindingResult result) {
        donationService.save(donation);
        return "redirect:confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmationAction() {
        return "form-confirmation";
    }

    @GetMapping("/email")
    @ResponseBody
    public String emailSend(@RequestParam String name, @RequestParam String surname, @RequestParam String message) {
//        emailService.sendSimpleMessage("psolski@poczta.onet.pl", "tytuł testowy", "text");
        return "Email sent";
    }

    @PostMapping("/email")
    @ResponseBody
    public String emailSendPost(@RequestParam String name, @RequestParam String surname, @RequestParam String message) {
        String messageText = name + " "  + surname + System.getProperty("line.separator") + " przesyła wiadomość: \r\n" + message;
        emailService.sendSimpleMessage("psolski@poczta.onet.pl", "Kontakt z aplikacji", messageText);
        return "Email sent post: " + messageText;
    }


}
