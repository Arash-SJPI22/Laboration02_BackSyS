package lernia.backosys.laboration02.controller;

import lernia.backosys.laboration02.entities.Coordination;
import lernia.backosys.laboration02.entities.PlaceDto;
import lernia.backosys.laboration02.service.PlaceService;
import org.springframework.http.ResponseEntity;
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
    public List<PlaceDto> getAllPublicPlaces() {
        return placeService.getAllPlacesByStatus("public");
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<PlaceDto>> gettAllPublicPlacesFromCategory(@PathVariable String category) {
        var placeRespons = placeService.gettAllPublicPlacesFromCategory(category);
        if (placeRespons.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(placeRespons);
    }

    @PostMapping
    public ResponseEntity<Void> createNewPlace(@RequestBody Coordination coordination){
        var place = placeService.createNewPlace(coordination);
        if (place.isPresent())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
    // @PatchMapping

    // @DeleteMapping
}
