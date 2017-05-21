package movietickets.session.service;

import movietickets.seat.Seat;
import movietickets.session.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Session's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface SessionService {
    /**
     * Adds new session with given hall.
     *
     * @param session new session
     * @param hallId id of the hall that session belongs to
     */
    void add(Session session, UUID hallId);

    /**
     * Gets session with specified id.
     *
     * @param id session's id
     * @return session with specified id
     * or {@code null} if it wasn't found
     */
    Session get(UUID id);

    /**
     * Gets all existing sessions as list.
     *
     * @return list of sessions
     */
    List<Session> getAll();

    /**
     * Deletes session.
     *
     * @param session session to delete
     */
    void delete(Session session);

    /**
     * Deletes session.
     *
     * @param id id of the session to delete
     */
    void delete(UUID id);

    /**
     * Changes the starting date time of the session.
     *
     * @param sessionId session's id
     * @param newTime new date time
     */
    void changeTime(UUID sessionId, LocalDateTime newTime);

    /**
     * Changes the hall of the session.
     *
     * @param sessionId session's id
     * @param newHallId new hall's id
     */
    void changeHall(UUID sessionId, UUID newHallId);

    /**
     * Gets two-dimensional array of the seats to be displayed.
     * First dimension represents rows, second - seats.
     * Elements in the array can be {@code null} and represents
     * empty space.
     *
     * @param sessionId session's id
     * @return two-dimensional array of seats
     */
    Seat[][] getDisplayedSeats(UUID sessionId);
}
