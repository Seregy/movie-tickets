package movietickets.session.web;

import movietickets.cinema.Cinema;
import movietickets.seat.Seat;
import movietickets.session.Session;
import movietickets.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Session controller, responsible for giving pages
 * and resources related to session.
 *
 * @author Seregy
 */
@Controller
public class SessionController {
    private final SessionService sessionService;

    /**
     * Constructs new session controller with given Session Service.
     *
     * @param sessionService session service
     */
    @Autowired
    public SessionController(final SessionService sessionService) {
        this.sessionService = sessionService;
    }


    /**
     * Shows main sessions' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/session")
    public String showSessionsPages() {
        return "admin/session";
    }

    /**
     * Shows seats selection block for given session.
     *
     * @param id session's identifier
     * @return name of jsp-page
     */
    @GetMapping("/session/{id}")
    public ModelAndView selectSeats(@PathVariable("id") final String id) {
        ModelAndView modelAndView = new ModelAndView("admin/seats_selection");
        Seat[][] seats = sessionService.getDisplayedSeats(UUID.fromString(id));
        modelAndView.addObject("seats", seats);
        return modelAndView;
    }

    /**
     * Shows session, associated with specific movie.
     * Also allows filtering sessions by cinema and starting date.
     *
     * @param movieId movie's id
     * @param cinemaId movie's id
     * @param date session's date
     * @return name of the view
     */
    @GetMapping("/sessions")
    public ModelAndView showSessions(@RequestParam(value = "movie")
                                         final String movieId,
                                     @RequestParam(value = "cinema",
                                             required = false)
                                         final String cinemaId,
                                     @RequestParam(value = "date",
                                             required = false)
                                     @DateTimeFormat(iso =
                                             DateTimeFormat.ISO.DATE)
                                         final LocalDate date) {
        ModelAndView modelAndView = new ModelAndView("fragments/session_block");

        List<Session> sessions;
        if (cinemaId != null) {
            sessions = sessionService.getAllFuture(UUID.fromString(movieId),
                    UUID.fromString(cinemaId));
        } else {
            sessions = sessionService.getAllFuture(UUID.fromString(movieId));
        }

        if (date != null) {
            sessions = sessions.stream()
                    .filter(session -> LocalDate
                            .from(session.getSessionStart())
                            .isEqual(date))
                    .collect(Collectors.toList());
        }

        final Map<Cinema, List<Session>> byCinema;
        Map<Cinema, Map<LocalDate, List<Session>>> byCinemaAndDate;
        if (sessions.isEmpty()) {
            byCinemaAndDate = null;
        } else {
            byCinema = sessionService.groupByCinema(sessions);

            byCinemaAndDate = byCinema.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            t -> sessionService
                                    .groupByDate(t.getValue())));
        }


        modelAndView.addObject("cinemaSessionMap", byCinemaAndDate);
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
        sessionService.add(session,
                UUID.fromString(hallId),
                UUID.fromString(movieId));
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
        sessionService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
