package lernia.backosys.laboration02.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
public record CategoryDto(@NotNull @Size(max = 255) @NotEmpty @NotBlank String name, @Size(max = 255) String symbol,
                          @Size(max = 255) String description) implements Serializable {
    public CategoryDto(Category category) {
        this(category.getName(), category.getSymbol(), category.getDescription());
    }
}