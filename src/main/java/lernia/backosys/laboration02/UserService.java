package lernia.backosys.laboration02;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Optional<UserDto> getOneUserByName(String name) {
        return mapUserDto(Optional.ofNullable(userRepository.findUserByName(name)));
    }

    Optional<UserDto> mapUserDto (Optional<User> user) {
        if (user.isEmpty())
            return Optional.empty();
        var newUser = user.get();
        return Optional.of(new UserDto(newUser));
    }
}
