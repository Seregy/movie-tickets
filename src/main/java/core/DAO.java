package core;

import java.io.Serializable;
import java.util.List;

/**
 * Generic data access object, used for accessing objects in databases.
 *
 * @param <T> Object type used in dao
 * @param <I> Id used for searching
 * @author Seregy
 */
public interface DAO<T, I extends Serializable> {
    /**
     * Searches for object with specified key and returns it.
     *
     * @param id unique identifier of the object to be found
     * @return {@link T} object with specified id
     * or {@code null} if object wasn't found
     */
    T find(I id);

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
     * @param id unique identifier of the object to be deleted
     * @return true if operation was successful, false otherwise
     */
    boolean delete(I id);
}
