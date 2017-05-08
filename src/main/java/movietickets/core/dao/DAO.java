package movietickets.core.dao;

import java.util.List;
import java.util.UUID;

/**
 * Generic data access object, used for accessing objects in databases.
 *
 * @param <T> Object type used in dao
 * @author Seregy
 */
public interface DAO<T> {
    /**
     * Searches for object with specified {@link UUID} key and returns it.
     *
     * @param id {@code UUID} of the object to be found
     * @return {@link T} object with specified id
     * or {@code null} if object wasn't found
     */
    T find(UUID id);

    /**
     * Searches for all objects and return them as a list.
     *
     * @return list of {@link T} objects
     */
    List<T> findAll();

    /**
     * Adds new {@link T} object.
     *
     * @param object {@link T} object to be added
     * @return true if operation was successful, false otherwise
     */
    boolean add(T object);

    /**
     * Updates existing {@link T} object.
     * Object will be found by its id, all other data will be updated
     *
     * @param object {@link T} object to be updated
     * @return true if operation was successful, false otherwise
     */
    boolean update(T object);

    /**
     * Deletes specified {@link T} object.
     *
     * @param id {@code UUID} of the object to be deleted
     * @return true if operation was successful, false otherwise
     */
    boolean delete(UUID id);
}
