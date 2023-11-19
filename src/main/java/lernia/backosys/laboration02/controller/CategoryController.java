package lernia.backosys.laboration02.controller;

import lernia.backosys.laboration02.entities.Category;
import lernia.backosys.laboration02.entities.CategoryDto;
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
        public List<CategoryDto> getAll(){
            return categoryService.getAll();
    }

    @GetMapping("/{category}")
    public ResponseEntity<CategoryDto> getOneCategoryByName(@PathVariable String category){
        var categoryRespons = categoryService.getOneCategoryByName(category);
        if (categoryRespons.isPresent())
            return ResponseEntity.ok().body(categoryRespons.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createNewCategory(@RequestBody Category category) {
        var cat = categoryService.createNewCategory(category);
        if (cat.isPresent())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
