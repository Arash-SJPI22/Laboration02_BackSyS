package lernia.backosys.laboration02.entities.place;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lernia.backosys.laboration02.entities.user.UserDto;
import lernia.backosys.laboration02.entities.category.CategoryDto;
import lernia.backosys.laboration02.serializer.Point2DSerializer;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.io.Serializable;

/**
 * DTO for {@link Place}
 */
public record PlaceDto(@NotNull int id,
                       @NotNull @Size(max = 255) String name,
                       @NotNull CategoryDto category,
                       @NotNull UserDto user,
                       @NotNull String status,
                       @Size(max = 255) String description,
                       @JsonSerialize(using = Point2DSerializer.class) Point<G2D> coordinates) implements Serializable {
    public PlaceDto(Place place) {
        this(place.getId(), place.getName(), new CategoryDto(place.getCategory()), new UserDto(place.getUser()), place.getStatus(), place.getDescription(), place.getCoordinates());
    }
}