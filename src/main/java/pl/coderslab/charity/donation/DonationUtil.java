package pl.coderslab.charity.donation;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonationUtil {


    /**
     * Returns number of categories selected on the first form
     * @param categoryContainer
     * @return number of selected categories
     */
    public int numberOfSelectedCategories(CategoryContainer categoryContainer) {
        return (int) categoryContainer.getCategories().stream().filter(Category::isChosen).count();
    }

    /**
     * Returns number of categories selected on the first form
     * @param categories
     * @return number of selected categories
     */
    public int numberOfSelectedCategories(List<Category> categories) {
        return (int) categories.stream().filter(Category::isChosen).count();
    }

    /**
     * Returns array of selected categories ids to be stored in the session
     * @param categoryContainer
     * @return array of selected categories ids
     */
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


    /**
     * Returns array of selected categories ids to be stored in the session
     * @param categories
     * @return array of selected categories ids
     */
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

    /**
     * Finds missing donation data and return name of the first form where data need to be filled
     * @param donation
     * @return name of the form to which needs to be redirected
     */
    public String getFirstFormWithWrongData(Donation donation) {
        if (donation.getCategories() == null || donation.getCategories().isEmpty()) {
            return "donate";
        }
        if (donation.getQuantity() < 1) {
            return "quantity";
        }
        if (donation.getInstitution() == null) {
            return "institution";
        }
        if (!isDeliveryCorrect(donation)) {
            return "address";
        }
        return null;
    }


    /**
     * Clears from session all data related to the donation
     * @param session
     */
    public void clearSessionDeliveryData(HttpSession session) {
        session.removeAttribute("donation");
        session.removeAttribute("quantity");
        session.removeAttribute("institutionId");
        session.removeAttribute("categoriesIds");
    }


    /**
     * Checks data posted against data injection
     * @param donation
     * @return true if data for donation are complete and correct
     */
    public boolean isDeliveryCorrect(Donation donation) {
        if (donation.getCity() == null || donation.getCity().isEmpty()
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

    /**
     * Sets in model message to be presented when form is stored in DB
     * @param model
     */
    public void setFormAcceptedMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Dziękujemy za przesłanie formularza.");
        messages.add("Na maila prześlemy wszelkie informacje o odbiorze.");
        model.addAttribute("messages", messages);
    }

    /**
     * Sets in model message to be presented when email was sent
     * @param model
     */
    public void setEmilOkMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Dziękujemy za kontakt.");
        messages.add("Na pytania odpowiadamy w ciągu 24 godzin.");
        model.addAttribute("messages", messages);
    }

    /**
     * Sets in model message to be presented when email was not sent
     * @param model
     */
    public void setEmailErrorMessage(Model model){
        List<String> messages = new ArrayList();
        messages.add("Oooops! Coś poszło nie tak.");
        messages.add("Nieudana wysyłka maila.");
        messages.add("Spróbuj ponownie za chwilę.");
        model.addAttribute("messages", messages);
    }

}
