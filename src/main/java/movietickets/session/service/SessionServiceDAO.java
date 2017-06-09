package movietickets.session.service;

import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import movietickets.hall.layout.SeatStatus;
import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import movietickets.seat.Seat;
import movietickets.session.Session;
import movietickets.session.dao.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    @PreAuthorize("hasAuthority('PM_ADD')")
    @Transactional
    @Override
    public void add(final Session session,
                    final UUID hallId,
                    final UUID movieId) {
        Hall hall = hallDAO.find(hallId);
        Layout layout = hall.getLayout();
        SeatStatus[][] seatStatuses = layout.getSeatsStatuses();

        boolean rowChanged = false;
        int actualRow = 1;
        for (SeatStatus[] seatsRow : seatStatuses) {
            int actualSeat = 1;
            for (SeatStatus seatStatus : seatsRow) {
                if (seatStatus == SeatStatus.REGULAR) {
                    if (rowChanged) {
                        actualRow++;
                        rowChanged = false;
                    }
                    session.addSeat(new Seat(actualRow, actualSeat++));
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
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void delete(final Session session) {
        delete(session.getId());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final UUID id) {
        sessionDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeTime(final UUID sessionId, final LocalDateTime newTime) {
        Session session = sessionDAO.find(sessionId);
        session.setSessionStart(newTime);
        sessionDAO.update(session);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeHall(final UUID sessionId, final UUID newHallId) {
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
        SeatStatus[][] seatStatuses = layout.getSeatsStatuses();

        boolean rowChanged = false;
        int actualRow = 1;

        for (int displayedRow = 0;
             displayedRow < seats.length;
             displayedRow++) {
            int actualSeat = 1;

            for (int displayedSeat = 0;
                 displayedSeat < seats[displayedRow].length;
                 displayedSeat++) {
                if (seatStatuses[displayedRow][displayedSeat]
                        == SeatStatus.REGULAR) {
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
