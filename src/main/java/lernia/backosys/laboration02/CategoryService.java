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

    List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::new)
                .toList();
    }

    Optional<CategoryDto> getOneCategoryByName(String category) {
        return mapCategoryDto(Optional.ofNullable(categoryRepository.findCategoryByName(category)));
    }

    static Optional<CategoryDto> mapCategoryDto(Optional<Category> category) {
        if (category.isEmpty())
            return Optional.empty();
        var newCategory = category.get();
        return Optional.of(new CategoryDto(newCategory));
    }

    public Optional<Category> createNewCategory(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName()))
            return Optional.empty();

        return Optional.of(categoryRepository.save(category));
    }
}
