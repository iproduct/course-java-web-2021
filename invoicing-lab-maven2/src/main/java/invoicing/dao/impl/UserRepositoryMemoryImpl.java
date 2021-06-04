package invoicing.dao.impl;

import invoicing.dao.KeyGenerator;
import invoicing.dao.UserRepository;
import invoicing.model.User;

import java.util.Optional;

public class UserRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, User>
    implements UserRepository {
    public UserRepositoryMemoryImpl() {
    }

    public UserRepositoryMemoryImpl(KeyGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .peek(u -> System.out.println("Filtered value: " + u))
                .findFirst();
    }
}
