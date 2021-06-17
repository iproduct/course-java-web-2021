package invoicing.dao;

import invoicing.entity.User;

import java.util.Optional;

public interface UserRepository extends Repository <Long, User> {
    Optional<User> findByUsername(String username);
}
