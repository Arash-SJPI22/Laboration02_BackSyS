package lernia.backosys.laboration02;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    Category findCategoryByName(String category);
}
