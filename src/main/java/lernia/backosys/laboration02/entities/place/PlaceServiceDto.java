package lernia.backosys.laboration02.entities.place;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record PlaceServiceDto(@NotNull @Size(max = 255) String name,
                              @NotNull String categoryName,
                              @NotNull Status status,
                              @Size(max = 255) String description,
                              //@NotNull @JsonSerialize(using = Point2DSerializer.class) Point<G2D> coordinates) implements Serializable {
                              @NotNull Coordination coordination) implements Serializable {
}
