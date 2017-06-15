package movietickets.session.service;

import movietickets.cinema.Cinema;
import movietickets.hall.Hall;
import movietickets.movie.Movie;
import movietickets.seat.Seat;
import movietickets.session.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Session's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface SessionService {
    /**
     * Adds new session.
     *
     * @param session new session
     * @param hallId id of the hall that session belongs to
     * @param movieId id of the movie that session belongs to
     */
    void add(Session session, UUID hallId, UUID movieId);

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
     * Gets all sessions of the cinema
     * as list.
     *
     * @param cinemaId cinema's id
     * @return list of sessions
     */
    List<Session> getAll(UUID cinemaId);

    /**
     * Gets all sessions that will
     * start in future as a list.
     * This method excludes sessions, that have
     * already started.
     *
     * @return list of future sessions
     */
    List<Session> getAllFuture();

    /**
     * Gets all sessions that will
     * start in future as a list.
     * This method excludes sessions, that have
     * already started.
     *
     * @param movieId movie's id
     * @return list of future sessions
     */
    List<Session> getAllFuture(UUID movieId);

    /**
     * Gets all sessions that will
     * start in future as a list.
     * This method excludes sessions, that have
     * already started.
     *
     * @param movieId movie's id
     * @param cinemaId cinema's id.
     * @return list of future sessions
     */
    List<Session> getAllFuture(UUID movieId, UUID cinemaId);

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

    /**
     * Groups given sessions by cinema they belong to.
     *
     * @param sessions sessions to be grouped
     * @return map with sessions, grouped by cinema
     */
    Map<Cinema, List<Session>> groupByCinema(Collection<Session> sessions);

    /**
     * Groups given sessions by movie they belong to.
     *
     * @param sessions sessions to be grouped
     * @return map with sessions, grouped by movie
     */
    Map<Movie, List<Session>> groupByMovie(Collection<Session> sessions);

    /**
     * Groups given sessions by their starting date.
     *
     * @param sessions sessions to be grouped
     * @return map with sessions, grouped by starting date
     */
    Map<LocalDate, List<Session>> groupByDate(Collection<Session> sessions);

    /**
     * Groups given sessions by hall they belong to.
     *
     * @param sessions sessions to be grouped
     * @return map with sessions, grouped by hall
     */
    Map<Hall, List<Session>> groupByHall(Collection<Session> sessions);
}
