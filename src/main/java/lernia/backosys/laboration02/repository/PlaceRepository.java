package lernia.backosys.laboration02.repository;

import lernia.backosys.laboration02.entities.Place;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PlaceRepository extends ListCrudRepository<Place, Integer> {

    List<Place> findPlaceByStatusAndCategoryName(String status, String category);

    List<Place> findByStatus(String status);
}
