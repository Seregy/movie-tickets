package movietickets.user.role.service;

import movietickets.user.role.Role;

import java.util.List;
import java.util.UUID;

/**
 * Created by Seregy on 26.05.2017.
 */
public interface RoleService {
    /**
     * Registers new user.
     *
     * @param user new user
     */
    void add(String name);


    /**
     * Gets user with specified id.
     *
     * @param id user's id
     * @return user with specified id
     * or {@code null} if it wasn't found
     */
    Role get(UUID id);

    /**
     * Gets all existing users as list.
     *
     * @return list of users
     */
    List<Role> getAll();

    /**
     * Deletes user.
     *
     * @param user user to delete
     */
    void delete(Role role);

    /**
     * Deletes user.
     *
     * @param id id of the user to delete
     */
    void delete(UUID id);

    /**
     * Changes the full name of the user.
     *
     * @param userId user's id
     * @param newFullName new full name
     */
    void changeName(UUID roleId, String newName);
}
