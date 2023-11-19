package lernia.backosys.laboration02;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    List<Category> getAll() {
        return categoryRepository.findAll();
    }

    Optional<Category> getOneCategoryByName(String category) {
        if (category.isEmpty())
            return Optional.empty();
        return Optional.ofNullable(categoryRepository.findCategoryByName(category));
    }
}
