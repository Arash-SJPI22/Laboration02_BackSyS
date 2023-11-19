package lernia.backosys.laboration02;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PlaceRepository extends ListCrudRepository<Place, Integer> {

    List<Place> getAllByStatusAndCategoryName(String status, String category);

    List<Place> getAllByStatus(String status);
}
