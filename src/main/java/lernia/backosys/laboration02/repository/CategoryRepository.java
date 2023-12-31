package lernia.backosys.laboration02.repository;

import lernia.backosys.laboration02.entities.category.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    Category findCategoryByName(String category);
}
