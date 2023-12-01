package lernia.backosys.laboration02.entities.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto(@NotNull @Size(max = 255) String name) implements Serializable {
    public UserDto(User user) {
        this(user.getName());
    }
}