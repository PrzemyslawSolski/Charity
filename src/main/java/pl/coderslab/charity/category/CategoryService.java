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

    /**
     * Returns collection List of Categories containing only items
     * for which session contains their id, meaning they were selected
     * on the first form
     * @param session
     * @return List<Category> if session contains 'categoriesIds' attribute
     *          or null otherwise
     */
    public List<Category> getCategoriesFromSession(HttpSession session) {
        List<Category> categories = new ArrayList<>();
        long[] categoriesIds = (long[])session.getAttribute("categoriesIds");
        if(categoriesIds==null || categoriesIds.length==0){
            return null;
        }else {
            for (long categoryId : (long[]) session.getAttribute("categoriesIds")) {
                Category category = getOne(categoryId);
                category.setChosen(true);
                categories.add(category);
            }
            return categories;
        }
    }


    /**
     * Returns list of all categories stored in DB with properly set 'chosen'
     * for the once selected in the first form
     *
     * @param session
     * @return List<Category> with 'chosen' field correctly set
     */
    public List<Category> getCategoriesWithSelectedFromSession(HttpSession session) {
        List<Category> categoriesChosen = getCategoriesFromSession(session);
        List<Category> categoriesAll = findAll();
        if(categoriesChosen!= null && !categoriesChosen.isEmpty()) {
            for (Category category : categoriesChosen) {
//                categoriesAll.remove(categoryService.getOne(category.getId()));
//                categoriesAll.add(category);
                for (Category categoryAll : categoriesAll) {
                    if(categoryAll.getId() == category.getId()){
                        categoryAll.setChosen(true);
                    }
                }
            }
        }
        return categoriesAll;
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category getOne(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(long id) {
        categoryRepository.deleteCategoryFromJoinTable(id);
        categoryRepository.deleteById(id);
    }


}
