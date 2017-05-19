package movietickets.session.web;

import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import movietickets.hall.layout.SeatStatus;
import movietickets.movie.dao.MovieDAO;
import movietickets.seat.Seat;
import movietickets.session.Session;
import movietickets.session.dao.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Session controller, responsible for giving pages
 * and resources related to session.
 *
 * @author Seregy
 */
@Controller
@Transactional
public class SessionController {

    private final SessionDAO sessionDAO;
    private final MovieDAO movieDAO;
    private final HallDAO hallDAO;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs new session controller with given Session DAO,
     * Movie DAO and Hall DAO.
     *
     * @param sessionDAO session data access object
     * @param movieDAO movie data access object
     * @param hallDAO hall data access object
     */
    @Autowired
    public SessionController(final SessionDAO sessionDAO,
                             final MovieDAO movieDAO,
                             final HallDAO hallDAO) {
        this.sessionDAO = sessionDAO;
        this.movieDAO = movieDAO;
        this.hallDAO = hallDAO;
    }


    /**
     * Shows main sessions' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/session")
    public String showSessionsPages() {
        return "session";
    }

    /**
     * Shows seats selection block for given session.
     *
     * @param id session's identifier
     * @return name of jsp-page
     */
    @GetMapping("/session/{id}")
    public ModelAndView selectSeats(@PathVariable("id") final String id) {
        ModelAndView modelAndView = new ModelAndView("seats_selection");
        Session session = sessionDAO.find(UUID.fromString(id));
        Layout layout = session.getHall().getLayout();
        Seat[][] seats =
                new Seat[layout.getRowsAmount()][layout.getSeatsAmount()];
        SeatStatus[][] seatStatuses = layout.getSeatsStatuses();

        boolean rowChanged = false;
        int actualRow = 1;
        for (int row = 0; row < seats.length; row++) {
            int actualSeat = 1;
            for (int seat = 0; seat < seats[row].length; seat++) {
                if (seatStatuses[row][seat] == SeatStatus.REGULAR) {
                    if (rowChanged) {
                        actualRow++;
                        rowChanged = false;
                    }
                    seats[row][seat] = findSeatInSet(session.getSeats(),
                            actualRow, actualSeat++);
                }
            }
            rowChanged = true;
        }
        modelAndView.addObject("seats", seats);
        return modelAndView;
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

    /**
     * Shows table, filled with sessions.
     *
     * @return name of jsp-page
     */
    @GetMapping("/sessions")
    public ModelAndView showSessions() {
        ModelAndView modelAndView = new ModelAndView("sessions_table");
        modelAndView.addObject("sessions", sessionDAO.findAll());
        return modelAndView;
    }

    /**
     * Adds new session with given date-time of its beginning.
     *
     * @param dateTime session's beginning time
     * @param movieId movie's id, associated with this session
     * @param hallId hall's id, associated with this session
     * @return response code
     */
    @PostMapping("/sessions")
    public ResponseEntity addSession(@RequestParam("date_time")
                                  final String dateTime,
                                     @RequestParam("movie_id")
                                     final String movieId,
                                     @RequestParam("hall_id")
                                     final String hallId) {
        Session session = new Session(LocalDateTime.parse(dateTime));
        Hall hall = hallDAO.find(UUID.fromString(hallId));

        Layout layout = hall.getLayout();
        SeatStatus[][] seatStatuses = layout.getSeatsStatuses();

        boolean rowChanged = false;
        int actualRow = 1;
        for (int row = 0; row < seatStatuses.length; row++) {
            int actualSeat = 1;
            for (int seat = 0; seat < seatStatuses[row].length; seat++) {
                if (seatStatuses[row][seat] == SeatStatus.REGULAR) {
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
        movieDAO.find(UUID.fromString(movieId)).addSession(session);
        hall.addSession(session);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes session with given id.
     *
     * @param id identifier of the session
     * @return response code
     */
    @DeleteMapping("/sessions/{id}")
    public ResponseEntity deleteSession(@PathVariable("id") final String id) {
        sessionDAO.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
