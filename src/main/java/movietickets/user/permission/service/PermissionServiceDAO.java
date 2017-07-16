package movietickets.user.permission.service;

import movietickets.user.permission.Permission;
import movietickets.user.permission.dao.PermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Permission's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class PermissionServiceDAO implements PermissionService {
    private final PermissionDAO permissionDAO;

    /**
     * Constructs new permission service.
     *
     * @param permissionDAO permission data access object
     */
    @Autowired
    public PermissionServiceDAO(final PermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(null, 'Permission', 'add')")
    @Transactional
    @Override
    public void add(final String name) {
        Permission permission = new Permission(name);
        permissionDAO.add(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Permission get(final UUID id) {
        return permissionDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Permission> getAll() {
        return permissionDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#id, 'Permission', 'delete')")
    @Transactional
    @Override
    public void delete(@P("id") final UUID id) {
        permissionDAO.delete(id);
    }
}
