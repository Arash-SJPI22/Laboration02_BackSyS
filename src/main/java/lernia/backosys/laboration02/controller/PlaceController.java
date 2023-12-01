package lernia.backosys.laboration02.controller;

import jakarta.validation.Valid;
import lernia.backosys.laboration02.entities.place.PlaceDto;
import lernia.backosys.laboration02.entities.place.PlaceServiceDto;
import lernia.backosys.laboration02.entities.place.PlaceServiceDtoPut;
import lernia.backosys.laboration02.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/places")
public class PlaceController {

    private PlaceService placeService;

    PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlace(@RequestParam(required = false) String category, @RequestParam(required = false) Boolean onlyMyPlaces) {

        if (onlyMyPlaces == null)
            onlyMyPlaces = Boolean.FALSE;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken) && onlyMyPlaces.equals(Boolean.TRUE))
            return ResponseEntity.status(405).build();

        return placeService.getPlace(category, onlyMyPlaces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PlaceDto>> getOnePlaceById(@PathVariable int id, @RequestParam(required = false) Integer radius) {
        return placeService.getOnePlaceById(id, radius);
    }

    @PostMapping
    public ResponseEntity<PlaceDto> createNewPlace(@Valid @RequestBody PlaceServiceDto placeServiceDto){
        return placeService.createNewPlace(placeServiceDto);
    }

    @PutMapping
    public ResponseEntity<PlaceDto> putPlace(@Valid @RequestBody PlaceServiceDtoPut placeServiceDtoPut) {
        return placeService.putPlace(placeServiceDtoPut);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaceDto> deletePlace(@PathVariable int id) {
        return placeService.deletePlace(id);
    }
}
