package lernia.backosys.laboration02;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.io.Serializable;

/**
 * DTO for {@link Place}
 */
public record PlaceDto(@NotNull @Size(max = 255) String name, @NotNull CategoryDto category, @NotNull UserDto user,
                       @NotNull String status, @Size(max = 255) String description, @JsonSerialize(using = Point2DSerializer.class) Point<G2D> coordinates) implements Serializable {
    PlaceDto(Place place) {
        this(place.getName(), new CategoryDto(place.getCategory()), new UserDto(place.getUser()), place.getStatus(), place.getDescription(), place.getCoordinates());
    }
}