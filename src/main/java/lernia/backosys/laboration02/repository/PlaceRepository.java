package lernia.backosys.laboration02.repository;

import lernia.backosys.laboration02.entities.place.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends ListCrudRepository<Place, Integer> {

    Place findPlaceById(int id);

    List<Place> findPlaceByStatusAndCategoryName(String status, String category);

    List<Place> findPlaceByStatus(String status);

    List<Place> findPlaceByStatusAndId(String status, int id);

    @Query("SELECT p FROM Place p WHERE (ST_Distance_Sphere(p.coordinates, (SELECT coordinates FROM Place WHERE id = :id AND (status='public' OR user.name = ?#{ principal?.username }))) < :radius) AND (p.status='public' OR p.user.name = ?#{ principal?.username })")
    List<Place> findPlaceByUserAndIdAndRadius(@Param("id") int id, @Param("radius") Integer radius);

    @Query("SELECT p FROM Place p WHERE (ST_Distance_Sphere(p.coordinates, (SELECT coordinates FROM Place WHERE id = :id AND status='public')) < :radius) AND p.status='public'")
    List<Place> findPlaceByIdAndRadius(@Param("id") int id, @Param("radius") Integer radius);

    @Query("SELECT p FROM Place p WHERE p.user.name = ?#{ principal?.username }")
    List<Place> findPlaceByUser();

    @Query("SELECT p FROM Place p WHERE (p.id = :id AND p.status = 'public') OR (p.user.name = ?#{ principal?.username } AND p.id = :id)")
    List<Place> findPlaceByUserAndId(@Param("id") int id);

    @Query("SELECT p FROM Place p WHERE p.user.name = ?#{ principal?.username } AND p.category.name = :category")
    List<Place> findPlaceByUserAndCategory(@Param("category") String category);

    @Query("SELECT p FROM Place p WHERE p.id=:id")
    Optional<Place> findPlaceStatusById(@Param("id") int id);
}

