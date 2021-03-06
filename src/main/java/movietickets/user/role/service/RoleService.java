package movietickets.user.role.service;

import movietickets.user.permission.Permission;
import movietickets.user.role.Role;

import java.util.List;
import java.util.UUID;

/**
 * Role's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface RoleService {
    /**
     * Adds new role.
     *
     * @param name name
     */
    void add(String name);

    /**
     * Adds new role.
     *
     * @param name name
     * @param permissionsIds permissions' identifiers
     */
    void add(String name, UUID... permissionsIds);


    /**
     * Gets role.
     *
     * @param id role's id
     * @return role with specified id
     * or {@code null} if it wasn't found
     */
    Role get(UUID id);

    /**
     * Gets role's permissions.
     *
     * @param id role's id
     * @return role's permissions
     */
    List<Permission> getPermissions(UUID id);

    /**
     * Gets all existing roles.
     *
     * @return list of roles
     */
    List<Role> getAll();

    /**
     * Deletes role.
     *
     * @param id id of the role to delete
     */
    void delete(UUID id);

    /**
     * Changes the name of the role.
     *
     * @param roleId role's id
     * @param newName new name
     */
    void changeName(UUID roleId, String newName);

    /**
     * Changes permissions of the role.
     *
     * @param roleId role's id
     * @param newPermissions new permissions
     */
    void changePermissions(UUID roleId, UUID... newPermissions);
}
