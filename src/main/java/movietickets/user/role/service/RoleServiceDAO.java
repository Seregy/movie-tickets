package movietickets.user.role.service;

import movietickets.user.permission.dao.PermissionDAO;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Role's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class RoleServiceDAO implements RoleService {
    private final RoleDAO roleDAO;
    private final PermissionDAO permissionDAO;

    /**
     * Constructs new role service.
     *
     * @param roleDAO role data access object
     * @param permissionDAO permission data access object
     */
    @Autowired
    public RoleServiceDAO(final RoleDAO roleDAO,
                          final PermissionDAO permissionDAO) {
        this.roleDAO = roleDAO;
        this.permissionDAO = permissionDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void add(final String name) {
        Role role = new Role(name);
        roleDAO.add(role);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void add(final String name,
                    final UUID... permissionsIds) {
        Role role = new Role(name);
        for (UUID permissionId : permissionsIds) {
            role.addPermission(permissionDAO.find(permissionId));
        }
        roleDAO.add(role);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Role get(final UUID id) {
        return roleDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Role> getAll() {
        return roleDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(final UUID id) {
        roleDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changeName(final UUID roleId, final String newName) {
        Role role = roleDAO.find(roleId);
        role.setName(newName);
        roleDAO.update(role);
    }
}
