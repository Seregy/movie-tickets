package movietickets.session.service;

import movietickets.cinema.Cinema;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import movietickets.hall.layout.SeatType;
import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import movietickets.seat.Seat;
import movietickets.session.Session;
import movietickets.session.dao.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Session's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class SessionServiceDAO implements SessionService {
    private final SessionDAO sessionDAO;
    private final HallDAO hallDAO;
    private final MovieDAO movieDAO;

    /**
     * Constructs new seat service.
     *
     * @param sessionDAO session data access object
     * @param hallDAO hall data access object
     * @param movieDAO movie data access object
     */
    @Autowired
    public SessionServiceDAO(final SessionDAO sessionDAO,
                             final HallDAO hallDAO,
                             final MovieDAO movieDAO) {
        this.sessionDAO = sessionDAO;
        this.hallDAO = hallDAO;
        this.movieDAO = movieDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#session, 'add')")
    @Transactional
    @Override
    public void add(@P("session") final Session session,
                    final UUID hallId,
                    final UUID movieId) {
        Hall hall = hallDAO.find(hallId);
        Layout layout = hall.getLayout();
        SeatType[][] seatTypes = layout.getSeatsTypes();

        boolean rowChanged = false;
        int actualRow = 1;
        for (SeatType[] typeRow : seatTypes) {
            int actualSeat = 1;
            for (SeatType seatType : typeRow) {
                if (seatType == SeatType.REGULAR) {
                    if (rowChanged) {
                        actualRow++;
                        rowChanged = false;
                    }
                    session.addSeat(new Seat(actualRow, actualSeat++,
                            seatType, session.getDefaultPrice()));
                }
            }
            rowChanged = true;
        }

        sessionDAO.add(session);
        hall.addSession(session);
        hallDAO.update(hall);
        Movie movie = movieDAO.find(movieId);
        movie.addSession(session);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Session get(final UUID id) {
        return sessionDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Session> getAll() {
        return sessionDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Session> getAll(final UUID cinemaId) {
        return sessionDAO.findAll().stream()
                .filter(session -> session.getHall()
                        .getCinema().getId().equals(cinemaId))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Session> getAllFuture() {
        return sessionDAO.findAll().stream()
                .filter(session -> session.getSessionStart()
                        .isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Session> getAllFuture(final UUID movieId) {
        return sessionDAO.findAll().stream()
                .filter(session -> session.getSessionStart()
                        .isAfter(LocalDateTime.now())
                        && session.getMovie().getId().equals(movieId))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Session> getAllFuture(final UUID movieId, final UUID cinemaId) {
        return sessionDAO.findAll().stream()
                .filter(session -> session.getSessionStart()
                        .isAfter(LocalDateTime.now())
                        && session.getMovie().getId().equals(movieId)
                        && session.getHall().getCinema()
                        .getId().equals(cinemaId))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#id, 'Session', 'delete')")
    @Transactional
    @Override
    public void delete(@P("id") final UUID id) {
        sessionDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#sessionId, 'Session', 'edit')")
    @Transactional
    @Override
    public void changeTime(@P("sessionId") final UUID sessionId,
                           final LocalDateTime newTime) {
        Session session = sessionDAO.find(sessionId);
        session.setSessionStart(newTime);
        sessionDAO.update(session);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#sessionId, 'Session', 'edit')")
    @Transactional
    @Override
    public void changeHall(@P("sessionId") final UUID sessionId,
                           final UUID newHallId) {
        Session session = sessionDAO.find(sessionId);
        Hall hall = hallDAO.find(newHallId);
        session.setHall(hall);
        sessionDAO.update(session);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Seat[][] getDisplayedSeats(final UUID sessionId) {
        Session session = sessionDAO.find(sessionId);
        Layout layout = session.getHall().getLayout();
        Seat[][] seats =
                new Seat[layout.getRowsAmount()][layout.getSeatsAmount()];
        SeatType[][] seatTypes = layout.getSeatsTypes();

        boolean rowChanged = false;
        int actualRow = 1;

        for (int displayedRow = 0;
             displayedRow < seats.length;
             displayedRow++) {
            int actualSeat = 1;

            for (int displayedSeat = 0;
                 displayedSeat < seats[displayedRow].length;
                 displayedSeat++) {
                if (seatTypes[displayedRow][displayedSeat]
                        == SeatType.REGULAR) {
                    if (rowChanged) {
                        actualRow++;
                        rowChanged = false;
                    }
                    seats[displayedRow][displayedSeat]
                            = findSeatInSet(session.getSeats(),
                            actualRow, actualSeat++);
                }
            }
            rowChanged = true;
        }

        return seats;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Map<Cinema, List<Session>> groupByCinema(
            final Collection<Session> sessions) {
        return sessions.stream()
                .collect(Collectors.groupingBy(s ->
                        s.getHall().getCinema(),
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Map<Movie, List<Session>> groupByMovie(
            final Collection<Session> sessions) {
        return sessions.stream()
                .collect(Collectors.groupingBy(Session::getMovie,
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Map<LocalDate, List<Session>> groupByDate(
            final Collection<Session> sessions) {
        return sessions.stream()
                .collect(Collectors.groupingBy(s ->
                        LocalDate.from(s.getSessionStart()),
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Map<Hall, List<Session>> groupByHall(
            final Collection<Session> sessions) {
        return sessions.stream()
                .collect(Collectors.groupingBy(Session::getHall,
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    /**
     * Finds seat in given set with specified row and seat number.
     *
     * @param set set with seats
     * @param rowNumber row number
     * @param seatNumber seat number
     * @return seat with given row and seat number
     * or {@code null} if seat wasn't found
     */
    private Seat findSeatInSet(final Set<Seat> set,
                               final int rowNumber,
                               final int seatNumber) {
        for (Seat seat : set) {
            if (seat.getRowNumber() == rowNumber
                    && seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }

        return null;
    }
}
