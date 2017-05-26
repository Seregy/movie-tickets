package movietickets.user.role.service;

import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by Seregy on 26.05.2017.
 */
@Service
public class RoleServiceDAO implements RoleService {
    private final RoleDAO roleDAO;

    /**
     * Constructs new ticket service with given User DAO.
     *
     * @param userDAO user data access object
     */
    @Autowired
    public RoleServiceDAO(final RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Transactional
    @Override
    public void add(String name) {
        Role role = new Role(name);
        roleDAO.add(role);
    }

    @Transactional
    @Override
    public Role get(UUID id) {
        return roleDAO.find(id);
    }

    @Transactional
    @Override
    public List<Role> getAll() {
        return roleDAO.findAll();
    }

    @Transactional
    @Override
    public void delete(Role role) {
        delete(role.getId());
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        roleDAO.delete(id);
    }

    @Transactional
    @Override
    public void changeName(UUID roleId, String newName) {
        Role role = roleDAO.find(roleId);
        role.setName(newName);
        roleDAO.update(role);
    }
}
