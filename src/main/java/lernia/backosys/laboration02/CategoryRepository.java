package lernia.backosys.laboration02;

import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    Category findCategoryByName(String category);

    Boolean existsCategoryByName(String name);
}
