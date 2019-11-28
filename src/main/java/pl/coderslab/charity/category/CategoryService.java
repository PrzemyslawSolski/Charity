package pl.coderslab.charity.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoriesFromSession(HttpSession session){
        List<Category> categories = new ArrayList<>();

        for (long categoryId : (long[]) session.getAttribute("categoriesIds")) {
            categories.add(getOne(categoryId));
        }
        return categories;
    }

    public void save (Category category){
        categoryRepository.save(category);
    }

    public Category getOne (long id){
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public void deleteById(long id){
        categoryRepository.deleteCategoryFromJoinTable(id);
        categoryRepository.deleteById(id);
    }


}
