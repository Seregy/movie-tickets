package movietickets.city.service;

import movietickets.city.City;

import java.util.List;
import java.util.UUID;

/**
 * City's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface CityService {
    /**
     * Adds new city.
     *
     * @param city new city
     */
    void add(City city);

    /**
     * Gets city with specified id.
     *
     * @param id city's id
     * @return city with specified id
     * or {@code null} if it wasn't found
     */
    City get(UUID id);

    /**
     * Gets all existing cities as list.
     *
     * @return list of cities
     */
    List<City> getAll();

    /**
     * Deletes city.
     *
     * @param city city to delete
     */
    void delete(City city);

    /**
     * Deletes city.
     *
     * @param id id of the city to delete
     */
    void delete(UUID id);

    /**
     * Changes the name of the city.
     *
     * @param cityId city's id
     * @param newName new name
     */
    void changeName(UUID cityId, String newName);
}
