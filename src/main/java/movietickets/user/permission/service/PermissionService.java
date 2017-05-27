package movietickets.user.permission.service;


import movietickets.user.permission.Permission;

import java.util.List;
import java.util.UUID;

/**
 * Permission's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface PermissionService {
    /**
     * Adds new permission.
     *
     * @param name name
     */
    void add(String name);


    /**
     * Gets permission.
     *
     * @param id permission's id
     * @return permission with specified id
     * or {@code null} if it wasn't found
     */
    Permission get(UUID id);

    /**
     * Gets all existing permissions.
     *
     * @return list of permissions
     */
    List<Permission> getAll();

    /**
     * Deletes permission.
     *
     * @param permission permission to delete
     */
    void delete(Permission permission);

    /**
     * Deletes permission.
     *
     * @param id id of the permission to delete
     */
    void delete(UUID id);
}
