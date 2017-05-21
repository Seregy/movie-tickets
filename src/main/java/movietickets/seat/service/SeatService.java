package movietickets.seat.service;

import movietickets.seat.Seat;

import java.util.List;
import java.util.UUID;

/**
 * Seat's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface SeatService {
    /**
     * Adds new seat.
     *
     * @param seat new seat
     */
    void add(Seat seat);

    /**
     * Gets seat with specified id.
     *
     * @param id seat's id
     * @return seat with specified id
     * or {@code null} if it wasn't found
     */
    Seat get(UUID id);

    /**
     * Gets all existing seats as list.
     *
     * @return list of seats
     */
    List<Seat> getAll();

    /**
     * Deletes seat.
     *
     * @param seat seat to delete
     */
    void delete(Seat seat);

    /**
     * Deletes seat.
     *
     * @param id id of the seat to delete
     */
    void delete(UUID id);
}
