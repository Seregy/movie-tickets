package movietickets.user.role.service;

import movietickets.user.permission.Permission;
import movietickets.user.permission.dao.PermissionDAO;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @PreAuthorize("hasPermission(null, 'Role', 'add')")
    @Transactional
    @Override
    public void add(final String name) {
        Role role = new Role(name);
        roleDAO.add(role);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(null, 'Role', 'add')")
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
    public List<Permission> getPermissions(final UUID id) {
        return new ArrayList<>(roleDAO.find(id).getPermissions());
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
    @PreAuthorize("hasPermission(#id, 'Role', 'delete')")
    @Transactional
    @Override
    public void delete(@P("id") final UUID id) {
        roleDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#roleId, 'Role', 'edit')")
    @Transactional
    @Override
    public void changeName(@P("roleId") final UUID roleId,
                           final String newName) {
        Role role = roleDAO.find(roleId);
        role.setName(newName);
        roleDAO.update(role);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#roleId, 'Role', 'edit')")
    @Transactional
    @Override
    public void changePermissions(final UUID roleId,
                                  final UUID... newPermissions) {
        Role role = roleDAO.find(roleId);
        List<Permission> permissions = new ArrayList<>(role.getPermissions());
        for (Permission permission : permissions) {
            role.removePermission(permission);
        }
        for (UUID permissionId : newPermissions) {
            Permission permission = permissionDAO.find(permissionId);
            role.addPermission(permission);
        }
        roleDAO.update(role);
    }
}
