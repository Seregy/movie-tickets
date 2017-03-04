package main.java.cinema;

import java.util.List;

/**
 * Data access object for {@link Cinema}.
 *
 * @author Seregy
 */
public interface CinemaDAO {
    /**
     * Searches for cinema with specified id and returns it.
     *
     * @param id unique identifier of the cinema to be found
     * @return {@link Cinema} object with specified id
     * or null if cinema wasn't found
     */
    Cinema find(int id);

    /**
     * Searches for cinemas with specified name and returns them as a list.
     *
     * @param name full name of the cinema
     * @return list of {@link Cinema} objects
     */
    List<Cinema> find(String name);

    /**
     * Searches for all cinemas and return them as a list.
     *
     * @return list of {@link Cinema} objects
     */
    List<Cinema> findAll();

    /**
     * Adds new {@link Cinema} object.
     *
     * @param cinema {@link Cinema} object to be added
     * @return true if operation was successful, false otherwise
     */
    boolean add(Cinema cinema);

    /**
     * Updates existing {@link Cinema} object.
     * Object will be found by its id, all other data will be updated
     *
     * @param cinema {@link Cinema} object to be updated
     * @return true if operation was successful, false otherwise
     */
    boolean update(Cinema cinema);

    /**
     * Deletes specified {@link Cinema} object.
     *
     * @param cinema {@link Cinema} object to be deleted
     * @return true if operation was successful, false otherwise
     */
    boolean delete(Cinema cinema);
}
