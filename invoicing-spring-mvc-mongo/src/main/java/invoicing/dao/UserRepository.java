package invoicing.dao;

import invoicing.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findByUsername(String username);
}
