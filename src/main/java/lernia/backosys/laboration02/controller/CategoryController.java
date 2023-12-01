package lernia.backosys.laboration02.controller;

import jakarta.validation.Valid;
import lernia.backosys.laboration02.entities.category.CategoryDto;
import lernia.backosys.laboration02.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

   @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(required = false) String category) {
        return categoryService.getCategories(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.createNewCategory(categoryDto);
    }
}
