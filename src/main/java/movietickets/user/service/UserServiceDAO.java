package movietickets.user.service;

import movietickets.user.User;
import movietickets.user.dao.UserDAO;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * User's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class UserServiceDAO implements UserService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    /**
     * Constructs new user service.
     *
     * @param userDAO user data access object
     * @param roleDAO role data access object
     */
    @Autowired
    public UserServiceDAO(final UserDAO userDAO, final RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void register(final User user) {
        userDAO.add(user);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void register(final String name, final String password,
                         final UUID roleId, final String email) {
        Role role = roleDAO.find(roleId);
        User user = new User(name, password, role, email);
        userDAO.add(user);
        role.addUser(user);
        roleDAO.update(role);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public User get(final UUID id) {
        return userDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public User get(final String name) {
        for (User user : userDAO.findAll()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(final User user) {
        delete(user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(final UUID id) {
        userDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changeName(final UUID userId, final String newName) {
        User user = userDAO.find(userId);
        user.setName(newName);
        userDAO.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changePassword(final UUID userId, final String newPassword) {
        User user = userDAO.find(userId);
        user.setPassword(newPassword);
        userDAO.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changeEmail(final UUID userId, final String newEmail) {
        User user = userDAO.find(userId);
        user.setEmail(newEmail);
        userDAO.update(user);
    }
}
