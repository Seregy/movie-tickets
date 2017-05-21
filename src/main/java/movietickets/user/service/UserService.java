package movietickets.user.service;

import movietickets.user.User;

import java.util.List;
import java.util.UUID;

/**
 * Ticket's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface UserService {
    /**
     * Registers new user.
     *
     * @param user new user
     */
    void register(User user);

    /**
     * Gets user with specified id.
     *
     * @param id user's id
     * @return user with specified id
     * or {@code null} if it wasn't found
     */
    User get(UUID id);

    /**
     * Gets all existing users as list.
     *
     * @return list of users
     */
    List<User> getAll();

    /**
     * Deletes user.
     *
     * @param user user to delete
     */
    void delete(User user);

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
    void changeFullName(UUID userId, String newFullName);

    /**
     * Changes the user name of the user.
     *
     * @param userId user's id
     * @param newUserName new user name
     */
    void changeUserName(UUID userId, String newUserName);

    /**
     * Changes the password of the user.
     *
     * @param userId user's id
     * @param newPassword new password
     */
    void changePassword(UUID userId, String newPassword);

    /**
     * Changes the email of the user.
     *
     * @param userId user's id
     * @param newEmail new email
     */
    void changeEmail(UUID userId, String newEmail);
}
