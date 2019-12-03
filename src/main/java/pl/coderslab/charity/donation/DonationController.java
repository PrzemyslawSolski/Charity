package pl.coderslab.charity.donation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.CharityApplication;
import pl.coderslab.charity.category.CategoryContainer;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.institution.InstitutionService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DonationController {
    private static final Logger logger = Logger.getLogger(DonationController.class);

    private final CategoryService categoryService;
    private final DonationService donationService;
    private final DonationUtil donationUtil;
    private final InstitutionService institutionService;
    private final EmailService emailService;

    @Autowired
    public DonationController(CategoryService categoryService, DonationService donationService, DonationUtil donationUtil, InstitutionService institutionService, EmailService emailService) {
        this.categoryService = categoryService;
        this.donationService = donationService;
        this.donationUtil = donationUtil;
        this.institutionService = institutionService;
        this.emailService = emailService;
    }

    @GetMapping("/donate")
    public String donateAction(Model model, HttpSession session) {
        CategoryContainer categoryContainer = new CategoryContainer();
        categoryContainer.setCategories(categoryService.getCategoriesWithSelectedFromSession(session));
        model.addAttribute("categoryContainer", categoryContainer);
//        model.addAttribute("categories", categoryService.getCategoriesWithSelectedFromSession(session));
        return "categories";
    }

    @PostMapping("/donate")
    public String donateAction(Model model, HttpSession session,
                               @ModelAttribute CategoryContainer categoryContainer,
//                               @ModelAttribute List<Category> categories,
                               BindingResult result) {
        if (donationUtil.numberOfSelectedCategories(categoryContainer) == 0) {
//        if (donationService.numberOfSelectedCategories(categories) == 0) {
            result.addError(new FieldError("categoryContainer", "categories",
                    "Musisz wybrać conajmniej jedną pozycję"));
        }
        if (result.hasErrors()) {
            return "categories";
        }
        session.setAttribute("categoriesIds", donationUtil.getSelectedCategoriesIds(categoryContainer));
//        session.setAttribute("categoriesIds", donationService.getSelectedCategoriesIds(categories));
        return "redirect:quantity#data";
    }

    @GetMapping("/quantity")
    public String quantityAction(Model model) {
        return "quantity";
    }

    @PostMapping("/quantity")
    public String quantityAction(@RequestParam Integer quantity, HttpSession session) {
        session.setAttribute("quantity", quantity);
        return "redirect:institution#data";
    }

    @GetMapping("/institution")
    public String organizationAction(Model model) {
        model.addAttribute("institutions", institutionService.findAll());
        return "institution";
    }

    @PostMapping("/institution")
    public String organizationAction(@RequestParam Long institution, HttpSession session) {
        if (!donationService.isInstitutionIdOk(institution)) {
            return "redirect:institution#data";
        }
        session.setAttribute("institutionId", institution);
        return "redirect:address#data";
    }

    @GetMapping("/address")
    public String addressAction(Model model, HttpSession session) {

        Donation donation = (Donation) session.getAttribute("donation");
        if (donation == null) {
            model.addAttribute("donation", new Donation());
        } else {
            model.addAttribute("donation", donation);
        }
        return "address";
    }

    @PostMapping("/address")
    public String addressAction(Model model, HttpSession session, @Validated({DeliveryValidationGroup.class}) @ModelAttribute Donation donation, BindingResult result) {
        if (donation != null
                && donation.getPickUpDate() != null
                && donation.getPickUpDate().isBefore(LocalDate.now().plusDays(1))) {
            result.addError(new FieldError("donation", "pickUpDate",
                    "Nie możemy przyjechać wcześniej niż jutro"));
        }
        if (result.hasErrors()) {
            return "address";
        }
        session.setAttribute("donation", donation);
        return "redirect:summary#data";
    }

    @GetMapping("/summary")
    public String summaryAction(Model model, HttpSession session) {
        Donation donation = donationService.collectDonationFromSession(session);
        String form = donationUtil.getFirstFormWithWrongData(donation);
        if (form != null) {
            return "redirect:" + form + "#data";
        }
        model.addAttribute("donation", donation);
        return "summary";
    }

    @PostMapping("/summary")
    public String summaryAction(Model model, HttpSession session, @ModelAttribute Donation donation, BindingResult result) {
        String form = donationUtil.getFirstFormWithWrongData(donation);
        if (form != null) {
            return "redirect:" + form + "#data";
        }
        donationService.save(donation);
        donationUtil.clearSessionDeliveryData(session);
        return "redirect:confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmationAction(Model model) {
        donationUtil.setFormAcceptedMessage(model);
//        List<String> messages = new ArrayList();
//        messages.add("Dziękujemy za przesłanie formularza.");
//        messages.add("Na maila prześlemy wszelkie informacje o odbiorze.");
//        model.addAttribute("messages", messages);
        return "confirmation";
    }

    @PostMapping("/email")
    public String emailSendPost(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String message,
                                @RequestParam String email,
                                Model model
    ) {
        String messageText = name + " " + surname + System.getProperty("line.separator")
                + "Email: " + email + System.getProperty("line.separator")
                + " przesyła wiadomość: \r\n" + message;
        try {
            if (CharityApplication.SEND_MAIL) {
                emailService.sendSimpleMessage("psolski@poczta.onet.pl", "Kontakt z aplikacji", messageText);
//        emailService.sendSimpleMessage("marcin.cieslak@coderslab.pl", "Kontakt z aplikacji", messageText);
            }
        } catch (Exception e) {
            logger.error("Email not sent", e);
            donationUtil.setEmailErrorMessage(model);
//            List<String> messages = new ArrayList();
//            messages.add("Oooops! Coś poszło nie tak.");
//            messages.add("Nieudana wysyłka maila.");
//            messages.add("Spróbuj ponownie za chwilę.");
//            model.addAttribute("messages", messages);
            return "confirmation";
        }
        donationUtil.setEmilOkMessage(model);
//        List<String> messages = new ArrayList();
//        messages.add("Dziękujemy za kontakt.");
//        messages.add("Na pytania odpowiadamy w ciągu 24 godzin.");
//        model.addAttribute("messages", messages);
        return "confirmation";
    }

}
