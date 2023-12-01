package lernia.backosys.laboration02.repository;

import lernia.backosys.laboration02.entities.user.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {

    User findUserByName(String name);
}
