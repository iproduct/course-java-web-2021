package invoicing.service.impl;

import invoicing.dao.UserRepository;
import invoicing.entity.User;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User addUser(User user) {
        user.setId(null);
        Date now = new Date();
//        user.setCreated(now);
//        user.setModified(now);
        return userRepo.save(user);
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
        getUserById(user.getId());
//        user.setModified(new Date());
        return userRepo.insert(user);
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
