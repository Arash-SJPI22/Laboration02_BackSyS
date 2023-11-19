package lernia.backosys.laboration02;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("")
        public List<Category> getAll(){
            return categoryService.getAll();
    }

    @GetMapping("/{category}")
    public ResponseEntity<Category> getOneCategoryByName(@PathVariable String category){
        var categoryRespons = categoryService.getOneCategoryByName(category);
        if (categoryRespons.isPresent())
            return ResponseEntity.ok().body(categoryRespons.get());
        return ResponseEntity.notFound().build();
    }
}
