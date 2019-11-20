package pl.coderslab.charity.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save (Category category){
        categoryRepository.save(category);
    }

    public Category getOne (long id){
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> categories(){
        return categoryRepository.findAll();
    }

    public void deleteById(long id){
        categoryRepository.deleteById(id);
    }


}
