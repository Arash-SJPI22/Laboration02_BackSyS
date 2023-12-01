package lernia.backosys.laboration02.service;

import jakarta.annotation.Nullable;
import lernia.backosys.laboration02.entities.category.Category;
import lernia.backosys.laboration02.entities.category.CategoryDto;
import lernia.backosys.laboration02.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private ResponseEntity<List<CategoryDto>> sendEmptyResponsIfEmpty(List<CategoryDto> categoryRespons) {
        if (categoryRespons.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(categoryRespons);
    }

    public ResponseEntity<List<CategoryDto>> getCategories(@Nullable String category){

        if (category == null || category.isEmpty()) {
            return sendEmptyResponsIfEmpty(categoryRepository.findAll()
                    .stream()
                    .map(CategoryDto::new)
                    .toList());
        }

        return sendEmptyResponsIfEmpty(mapCategoryDto(
                Optional.ofNullable(categoryRepository.findCategoryByName(category)))
                .stream().
                toList());
    }

    static Optional<CategoryDto> mapCategoryDto(Optional<Category> category) {
        if (category.isEmpty())
            return Optional.empty();
        return Optional.of(new CategoryDto(category.get()));
    }

    public ResponseEntity<CategoryDto> createNewCategory(CategoryDto categoryDto) {
        if (categoryRepository.findCategoryByName(categoryDto.name()) != null)
            return ResponseEntity.badRequest().build();

        Category category = new Category();

        category.setName(categoryDto.name());
        category.setSymbol(categoryDto.symbol());
        category.setDescription(categoryDto.description());

        categoryRepository.save(category);

        return ResponseEntity.ok().body(new CategoryDto(category));
    }

    public Category findCategoryByName(String category) {
        return categoryRepository.findCategoryByName(category);
    }
}
