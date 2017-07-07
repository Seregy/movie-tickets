package movietickets.user.service;

import movietickets.ticket.Ticket;
import movietickets.user.User;

import java.util.List;
import java.util.UUID;

/**
 * User's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface UserService {
    /**
     * Registers new user.
     *
     * @param name user's name
     * @param password user's password
     * @param roleId user's role identifier
     * @param email user's email
     */
    void register(String name, String password, UUID roleId, String email);

    /**
     * Gets user with specified id.
     *
     * @param id user's id
     * @return user with specified id
     * or {@code null} if it wasn't found
     */
    User get(UUID id);

    /**
     * Gets user with specified name.
     *
     * @param name user's name
     * @return user with specified id
     * or {@code null} if it wasn't found
     */
    User get(String name);

    /**
     * Gets all existing users as list.
     *
     * @return list of users
     */
    List<User> getAll();

    /**
     * Gets all user's tickets.
     *
     * @param userId user's id
     * @return list of tickets
     */
    List<Ticket> getTickets(UUID userId);

    /**
     * Deletes user.
     *
     * @param id id of the user to delete
     */
    void delete(UUID id);

    /**
     * Changes the user name of the user.
     *
     * @param userId user's id
     * @param newName new user name
     */
    void changeName(UUID userId, String newName);

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
