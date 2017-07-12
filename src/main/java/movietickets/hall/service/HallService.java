package movietickets.hall.service;

import movietickets.hall.Hall;
import movietickets.hall.layout.Layout;

import java.util.List;
import java.util.UUID;

/**
 * Hall's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface HallService {
    /**
     * Add new hall with given layout and cinema.
     *
     * @param hall new hall
     * @param layout new layout
     * @param cinemaId id of the cinema that hall belongs to
     */
    void add(Hall hall, Layout layout, UUID cinemaId);

    /**
     * Gets hall with specified id.
     *
     * @param id hall's id
     * @return hall with specified id
     * or {@code null} if it wasn't found
     */
    Hall get(UUID id);

    /**
     * Gets all existing halls as list.
     *
     * @return list of halls
     */
    List<Hall> getAll();

    /**
     * Gets all halls of the cinema.
     *
     * @param cinemaId cinema's id
     * @return list of halls
     */
    List<Hall> getAll(UUID cinemaId);

    /**
     * Deletes hall.
     *
     * @param id id of the hall to delete
     */
    void delete(UUID id);

    /**
     * Changes the name of the hall.
     *
     * @param hallId hall's id
     * @param newName new name
     */
    void changeName(UUID hallId, String newName);

    /**
     * Changes the layout of the hall.
     *
     * @param hallId hall's id
     * @param newLayout new layout
     */
    void changeLayout(UUID hallId, Layout newLayout);
}
