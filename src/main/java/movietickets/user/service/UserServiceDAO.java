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
     * Constructs new ticket service with given User DAO.
     *
     * @param userDAO user data access object
     * @param roleDAO
     */
    @Autowired
    public UserServiceDAO(final UserDAO userDAO, RoleDAO roleDAO) {
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

    @Transactional
    @Override
    public void register(String fullName, String userName, String password, UUID roleId, String email) {
        Role role = roleDAO.find(roleId);
        User user = new User(fullName, userName, password, role, email);
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

    @Transactional
    @Override
    public User get(String name) {
        for (User user : userDAO.findAll()) {
            if (user.getUserName().equals(name)) {
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
    public void changeFullName(final UUID userId, final String newFullName) {
        User user = userDAO.find(userId);
        user.setFullName(newFullName);
        userDAO.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changeUserName(final UUID userId, final String newUserName) {
        User user = userDAO.find(userId);
        user.setUserName(newUserName);
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
