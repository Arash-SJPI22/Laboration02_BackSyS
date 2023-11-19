package lernia.backosys.laboration02.service;

import lernia.backosys.laboration02.entities.*;
import lernia.backosys.laboration02.repository.CategoryRepository;
import lernia.backosys.laboration02.repository.PlaceRepository;
import lernia.backosys.laboration02.repository.UserRepository;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Service
public class PlaceService {

    PlaceRepository placeRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public PlaceService(PlaceRepository placeRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<PlaceDto> getAllPlacesByStatus(String status) {
        return placeRepository.findByStatus(status)
                .stream()
                .map(PlaceDto::new)
                .toList();
    }

  public List<PlaceDto> getAllPublicPlacesFromCategory(String category) {
        return placeRepository.findPlaceByStatusAndCategoryName("public", category)
                .stream()
                .map(PlaceDto::new)
                .toList();
    }

    static Optional<PlaceDto> mapPlaceDto(Optional<Place> place){
        if (place.isEmpty())
            return Optional.empty();
        var newPlace = place.get();
        return Optional.of(
                new PlaceDto(
                newPlace.getName(), new CategoryDto(newPlace.getCategory()), new UserDto(newPlace.getUser()), newPlace.getStatus(), newPlace.getDescription(), newPlace.getCoordinates()
        ));
    }

    public Optional<Place> createNewPlace(Coordination coordination){
        if( (coordination.lat() < -90 || coordination.lat() > 90) || (coordination.lng() < -180 || coordination.lng() > 180) )
            return Optional.empty();
        var place = new Place();
        Category category = categoryRepository.findCategoryByName("Svampställen");
        User user = userRepository.findUserByName("Bengan");

        //String placeInText = "POINT (" + coordination.lng() + " " + coordination.lat() + ")";
        //Point<G2D> geo = (Point<G2D>) Wkt.fromWkt(placeInText, WGS84);

        var geo = Geometries.mkPoint(new G2D(coordination.lng(),coordination.lat()), WGS84);
        place.setName("Bengans Svampisar");
        place.setCategory(category);
        place.setUser(user);
        place.setStatus("public");
        place.setCreated(Instant.now());
        place.setUpdated(Instant.now());
        place.setDescription("Fan grappar, här var det fin fint!");
        place.setCoordinates(geo);

        return Optional.of(placeRepository.save(place));
    }
}
