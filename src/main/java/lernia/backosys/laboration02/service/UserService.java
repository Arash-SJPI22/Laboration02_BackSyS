package lernia.backosys.laboration02.service;

import lernia.backosys.laboration02.entities.user.User;
import lernia.backosys.laboration02.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
}
