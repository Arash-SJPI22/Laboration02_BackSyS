package lernia.backosys.laboration02.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lernia.backosys.laboration02.entities.category.Category;
import lernia.backosys.laboration02.entities.place.*;
import lernia.backosys.laboration02.entities.user.User;
import lernia.backosys.laboration02.repository.PlaceRepository;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Service
public class PlaceService {

    PlaceRepository placeRepository;
    CategoryService categoryService;
    UserService userService;

    public PlaceService(PlaceRepository placeRepository, CategoryService categoryService, UserService userService) {
        this.placeRepository = placeRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private ResponseEntity<List<PlaceDto>> sendEmptyResponsIfEmpty(List<PlaceDto> placeRespons) {
        if (placeRespons.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(placeRespons);
    }

    private Boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    private Boolean isUserAuthedForPlaceWithId(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var placeRespons = placeRepository.findPlaceStatusById(id);

        if (placeRespons.isEmpty())
            return false;
        else if (placeRespons.get().getStatus().equals("private") && authentication.getName().equals(placeRespons.get().getUser().getName()))
            return true;
        else
            return placeRespons.get().getStatus().equals("public");
    }

    private Boolean isCoordinationValid(@Valid Coordination coordination) {
        return (coordination.lat() >= -90 && coordination.lat() <= 90) && (coordination.lng() >= -180 && coordination.lng() <= 180);
    }

    public ResponseEntity<List<PlaceDto>> getPlace(@Nullable String category,@Nullable Boolean onlyMyPlaces) {

        if (onlyMyPlaces == null)
            onlyMyPlaces = Boolean.FALSE;

        if (!isUserAuthenticated() && onlyMyPlaces.equals(Boolean.TRUE))
            return ResponseEntity.status(406).build();

        if ((category == null || category.isEmpty()) && onlyMyPlaces.equals(Boolean.FALSE))
            return sendEmptyResponsIfEmpty(placeRepository.findPlaceByStatus("public")
                    .stream()
                    .map(PlaceDto::new)
                    .toList());

        else if ((category == null || category.isEmpty()) && onlyMyPlaces.equals(Boolean.TRUE))
            return sendEmptyResponsIfEmpty(placeRepository.findPlaceByUser()
                    .stream()
                    .map(PlaceDto::new)
                    .toList());

        else if (onlyMyPlaces.equals(Boolean.FALSE))
            return sendEmptyResponsIfEmpty(placeRepository.findPlaceByStatusAndCategoryName("public", category)
                    .stream()
                    .map(PlaceDto::new)
                    .toList());

        return sendEmptyResponsIfEmpty(placeRepository.findPlaceByUserAndCategory(category)
                .stream()
                .map(PlaceDto::new)
                .toList());
    }

    public ResponseEntity<List<PlaceDto>> getOnePlaceById(int id,@Nullable Integer radius) {
        if (!isUserAuthedForPlaceWithId(id))
            return ResponseEntity.status(401).build();

        if (radius == null && isUserAuthenticated())
            return sendEmptyResponsIfEmpty(placeRepository.findPlaceByUserAndId(id)
                    .stream()
                    .map(PlaceDto::new)
                    .toList());
        else if (radius == null)
            return sendEmptyResponsIfEmpty(placeRepository.findPlaceByStatusAndId("public",id)
                    .stream()
                    .map(PlaceDto::new)
                    .toList());

        else if (isUserAuthenticated())
            return sendEmptyResponsIfEmpty(placeRepository.findPlaceByUserAndIdAndRadius(id, radius)
                    .stream()
                    .map(PlaceDto::new)
                    .toList());// --- här ska en stop vara

        return sendEmptyResponsIfEmpty(placeRepository.findPlaceByIdAndRadius( id, radius)
                .stream()
                .map(PlaceDto::new)
                .toList());
        }

    @Transactional
    public ResponseEntity<PlaceDto> createNewPlace(@Valid PlaceServiceDto placeServiceDto){

        if(!isCoordinationValid(placeServiceDto.coordination()))
            return ResponseEntity.badRequest().build();

        var place = new Place();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Category category = categoryService.findCategoryByName(placeServiceDto.categoryName());
        User user = userService.findUserByName(authentication.getName());


        if (category == null)
            return ResponseEntity.badRequest().build();

        var geo = Geometries.mkPoint(new G2D(placeServiceDto.coordination().lng(),placeServiceDto.coordination().lat()), WGS84);

        place.setName(placeServiceDto.categoryName());
        place.setCategory(category);
        place.setUser(user);
        place.setStatus(placeServiceDto.status().toString());
        place.setCreated(Instant.now());
        place.setUpdated(Instant.now());
        place.setDescription(placeServiceDto.description());
        place.setCoordinates(geo);

        var savedPlace = placeRepository.save(place);

        return ResponseEntity.status(201).body(new PlaceDto(savedPlace));
    }

    @Transactional
    public ResponseEntity<PlaceDto> putPlace(@Valid PlaceServiceDtoPut placeServiceDtoPut) {
        var place = placeRepository.findPlaceById(placeServiceDtoPut.id());

        if ( place == null)
            return ResponseEntity.notFound().build();

        if (!isCoordinationValid(placeServiceDtoPut.coordination()))
            return ResponseEntity.badRequest().build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Category category = categoryService.findCategoryByName(placeServiceDtoPut.categoryName());
        User user = userService.findUserByName(authentication.getName());

        if (!authentication.getName().equalsIgnoreCase(place.getUser().getName()))
            return ResponseEntity.status(401).build();

        if (category == null)
            return ResponseEntity.badRequest().build();

        var geo = Geometries.mkPoint(new G2D(placeServiceDtoPut.coordination().lng(),placeServiceDtoPut.coordination().lat()), WGS84);

        place.setName(placeServiceDtoPut.categoryName());
        place.setCategory(category);
        place.setUser(user);
        place.setStatus(placeServiceDtoPut.status().toString());
        place.setCreated(place.getCreated());
        place.setUpdated(Instant.now());
        place.setDescription(placeServiceDtoPut.description());
        place.setCoordinates(geo);

        placeRepository.save(place);

        return ResponseEntity.ok().body(new PlaceDto(place));
    }

    @Transactional
    public ResponseEntity<PlaceDto> deletePlace(int id) {

        var place = placeRepository.findPlaceById(id);

        if (place == null)
            return ResponseEntity.badRequest().build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getName().equalsIgnoreCase(place.getUser().getName()))
            return ResponseEntity.status(401).build();

        placeRepository.delete(place);

        return ResponseEntity.ok().body(new PlaceDto(place));
    }
}
