package lernia.backosys.laboration02;

import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {

    User findUserByName(String name);
}
