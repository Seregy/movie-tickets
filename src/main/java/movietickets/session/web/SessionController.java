package movietickets.session.web;

import movietickets.seat.Seat;
import movietickets.session.Session;
import movietickets.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.UUID;

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
     * Shows table, filled with sessions.
     *
     * @return name of jsp-page
     */
    @GetMapping("/sessions")
    public ModelAndView showSessions() {
        ModelAndView modelAndView = new ModelAndView("admin/sessions_table");
        modelAndView.addObject("sessions", sessionService.getAll());
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
        sessionService.add(session, UUID.fromString(hallId));
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
