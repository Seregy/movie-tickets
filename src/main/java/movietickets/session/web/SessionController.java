package movietickets.session.web;

import movietickets.cinema.Cinema;
import movietickets.hall.Hall;
import movietickets.hall.service.HallService;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
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
import java.time.LocalTime;
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
@SessionAttributes("currentCity")
public class SessionController {
    private final SessionService sessionService;
    private final HallService hallService;
    private final MovieService movieService;

    /**
     * Constructs new session controller with given Session Service.
     *
     * @param sessionService session service
     * @param hallService hall service
     * @param movieService movie service
     */
    @Autowired
    public SessionController(final SessionService sessionService,
                             final HallService hallService,
                             final MovieService movieService) {
        this.sessionService = sessionService;
        this.hallService = hallService;
        this.movieService = movieService;
    }

    /**
     * Shows seats selection block for given session.
     *
     * @param id session's identifier
     * @return name of the view
     */
    @GetMapping("/session/{id}")
    public ModelAndView selectSeats(@PathVariable("id") final String id) {
        ModelAndView modelAndView = new ModelAndView("session");
        UUID sessionId = UUID.fromString(id);
        modelAndView.addObject("movieSession",
                sessionService.get(sessionId));
        modelAndView.addObject("movie",
                sessionService.get(sessionId).getMovie());
        modelAndView.addObject("cinema",
                sessionService.get(sessionId).getHall().getCinema());
        Seat[][] seats = sessionService.getDisplayedSeats(sessionId);
        modelAndView.addObject("seats", seats);
        return modelAndView;
    }

    /**
     * Shows session, associated with specific movie.
     * Also allows filtering sessions by cinema, starting date and
     * display technology.
     *
     * @param movieId movie's id
     * @param cinemaId movie's id
     * @param displayTechnology session's display technology
     * @param date session's date
     * @return name of the view
     */
    @GetMapping("/sessions")
    public ModelAndView showSessions(@RequestParam(value = "movie")
                                         final String movieId,
                                     @RequestParam(value = "cinema",
                                             required = false)
                                         final String cinemaId,
                                     @RequestParam(value = "technology",
                                             required = false)
                                         final String displayTechnology,
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

        if (displayTechnology != null && !displayTechnology.isEmpty()) {
            sessions = sessions.stream()
                    .filter(session -> session
                            .getTechnology().equals(displayTechnology))
                    .collect(Collectors.toList());
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
     * Shows list of all cinema's sessions
     * for admin panel.
     *
     * @param cinemaId cinema's id
     * @param search search query(for movie or hall names)
     * @param date date filter
     * @return model and view
     */
    @GetMapping("/admin/cinema/{id}/sessions")
    public ModelAndView showAdminSessions(@PathVariable("id")
                                            final UUID cinemaId,
                                          @RequestParam(value = "search",
                                                  required = false)
                                            final String search,
                                          @RequestParam(value = "date")
                                              @DateTimeFormat(iso =
                                                      DateTimeFormat.ISO.DATE)
                                            final LocalDate date) {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/session_block");
        List<Session> sessions = sessionService.getAll(cinemaId).stream()
                .filter(session -> LocalDate.from(session.getSessionStart())
                        .isEqual(date))
                .collect(Collectors.toList());

        if (search != null) {
            List<Session> filtered = sessions.stream()
                    .filter(session -> session.getMovie()
                            .getName().contains(search))
                    .collect(Collectors.toList());

            if (filtered.isEmpty()) {
                filtered = sessions.stream()
                        .filter(session -> session.getHall()
                                .getName().contains(search))
                        .collect(Collectors.toList());
            }

            sessions = filtered;
        }


        final Map<Movie, List<Session>> byMovie;
        Map<Movie, Map<Hall, List<Session>>> byMovieAndHall;
        if (sessions.isEmpty()) {
            byMovieAndHall = null;
        } else {
            byMovie = sessionService.groupByMovie(sessions);

            byMovieAndHall = byMovie.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            t -> sessionService
                                    .groupByHall(t.getValue())));
        }


        modelAndView.addObject("movieSessionMap", byMovieAndHall);
        return modelAndView;
    }

    /**
     * Adds new session.
     *
     * @param cinemaId cinema's id
     * @param dateTime session's beginning time
     * @param movieName movie's name
     * @param hallName hall's name
     * @param technology display technology
     * @return response entity
     */
    @PostMapping("/session")
    public ResponseEntity addSession(@RequestParam("cinema")
                                        final UUID cinemaId,
                                     @RequestParam("date_time")
                                        final String dateTime,
                                     @RequestParam("movie")
                                        final String movieName,
                                     @RequestParam("hall")
                                        final String hallName,
                                     @RequestParam("technology")
                                        final String technology) {
        Session session = new Session(LocalDateTime.parse(dateTime));
        session.setTechnology(technology);
        Movie movie = movieService.getAll().stream()
                .filter(m -> m.getName().equals(movieName))
                .findFirst().get();
        Hall hall = hallService.getAll(cinemaId).stream()
                .filter(h -> h.getName().equals(hallName))
                .findFirst().get();
        sessionService.add(session, hall.getId(), movie.getId());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing session.
     *
     * @param id session's id
     * @param time session's new name
     * @return response entity
     */
    @PostMapping("/session/{id}")
    public ResponseEntity editSession(@PathVariable("id")
                                     final UUID id,
                                     @RequestParam("time")
                                     final String time) {
        Session session = sessionService.get(id);
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime previous = session.getSessionStart();
        LocalDateTime newTime = LocalDateTime.of(previous.getYear(),
                previous.getMonth(),
                previous.getDayOfMonth(),
                localTime.getHour(),
                localTime.getMinute());
        sessionService.changeTime(id, newTime);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes session with given id.
     *
     * @param id identifier of the session
     * @return response code
     */
    @DeleteMapping("/session/{id}")
    public ResponseEntity deleteSession(@PathVariable("id") final String id) {
        sessionService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
