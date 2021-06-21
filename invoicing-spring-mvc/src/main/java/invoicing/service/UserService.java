package invoicing.service;

import invoicing.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getAllUsers();
    User getUserById(Long id);
    User addUser(User user);
    List<User> addUsersBatch(List<User> users);
    long dropAllUsers();
    User updateUser(User user);
    User deleteUserById(Long id);
    long getCount();
}
