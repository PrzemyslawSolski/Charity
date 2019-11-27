package pl.coderslab.charity.category;

import java.io.Serializable;
import java.util.List;

public class CategoryContainer {
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
