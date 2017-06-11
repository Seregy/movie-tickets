package movietickets.cinema.service;

import movietickets.cinema.Cinema;
import movietickets.city.City;

import java.util.List;
import java.util.UUID;

/**
 * Cinema's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface CinemaService {
    /**
     * Adds new cinema.
     *
     * @param cinema new cinema
     */
    void add(Cinema cinema);

    /**
     * Gets cinema with specified id.
     *
     * @param id cinema's id
     * @return cinema with specified id
     * or {@code null} if it wasn't found
     */
    Cinema get(UUID id);

    /**
     * Gets all existing cinemas as a list.
     *
     * @return list of cinemas
     */
    List<Cinema> getAll();

    /**
     * Gets all cinemas in specified city as a list.
     *
     * @param city city to search cinemas in
     * @return list of cinemas
     */
    List<Cinema> getAll(City city);

    /**
     * Deletes cinema.
     *
     * @param cinema cinema to delete
     */
    void delete(Cinema cinema);

    /**
     * Deletes cinema.
     *
     * @param id id of the cinema to delete
     */
    void delete(UUID id);

    /**
     * Changes the name of the cinema.
     *
     * @param cinemaId cinema's id
     * @param newName new name
     */
    void changeName(UUID cinemaId, String newName);

    /**
     * Changes the location of the cinema.
     *
     * @param cinemaId cinema's id
     * @param newLocation new location
     */
    void changeLocation(UUID cinemaId, String newLocation);
}
