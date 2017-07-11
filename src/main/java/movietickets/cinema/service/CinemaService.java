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
     * @param cityId city's id
     */
    void add(Cinema cinema, UUID cityId);

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
     * Changes cinema's city.
     *
     * @param cinemaId cinema's id
     * @param newCityId new city's id
     */
    void changeCity(UUID cinemaId, UUID newCityId);

    /**
     * Changes the address of the cinema.
     *
     * @param cinemaId cinema's id
     * @param newAddress new address
     */
    void changeAddress(UUID cinemaId, String newAddress);

    /**
     * Changes cinema's phone.
     *
     * @param cinemaId cinema's id
     * @param newPhone new phone
     */
    void changePhone(UUID cinemaId, String newPhone);

    /**
     * Changes cinema's website.
     *
     * @param cinemaId cinema's id
     * @param newWebsite new website
     */
    void changeWebsite(UUID cinemaId, String newWebsite);
}
