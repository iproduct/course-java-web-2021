package invoicing.service.impl;

import invoicing.dao.UserRepository;
import invoicing.entity.User;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public Collection<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with ID='%d' not found", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found.", username)));
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
//        Date now = new Date();
//        user.setCreated(now);
//        user.setModified(now);
        return userRepo.insert(user);
    }

    @Override
    public List<User> addUsersBatch(List<User> users) {
        return userRepo.saveAll(users);
    }

    @Override
    public long dropAllUsers() {
        long count = getCount();
        userRepo.deleteAll();
        return count;
    }

    @Override
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        user.setPassword(old.getPassword());
//        user.setModified(new Date());
        return userRepo.save(user);
    }

    @Override
    public User deleteUserById(String id) {
        User old = getUserById(id);
        userRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return userRepo.count();
    }
}
