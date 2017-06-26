package movietickets.core.web;

import com.google.common.collect.Lists;
import movietickets.cinema.Cinema;
import movietickets.cinema.service.CinemaService;
import movietickets.city.City;
import movietickets.hall.service.HallService;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import movietickets.ticket.service.TicketService;
import movietickets.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Main app controller, responsible for giving general pages/resources.
 *
 * @author Seregy
 */
@Controller
@SessionAttributes("currentCity")
public class AppController {
    /**
     * Number of movies, displayed in one row on the page.
     */
    public static final int MOVIE_ROW_SIZE = 6;
    private static Logger log = Logger.getLogger(AppController.class.getName());

    private final MovieService movieService;
    private final UserService userService;
    private final HallService hallService;
    private final TicketService ticketService;
    private final CinemaService cinemaService;

    /**
     * Constructs new app controller.
     *
     * @param movieService movie service
     * @param userService user service
     * @param hallService hall service
     * @param ticketService ticket service
     * @param cinemaService cinema service
     */
    @Autowired
    public AppController(final MovieService movieService,
                         final UserService userService,
                         final HallService hallService,
                         final TicketService ticketService,
                         final CinemaService cinemaService) {
        this.movieService = movieService;
        this.userService = userService;
        this.hallService = hallService;
        this.ticketService = ticketService;
        this.cinemaService = cinemaService;
    }

    /**
     * Shows main web-page.
     *
     * @param city current city
     * @return model and view
     */
    @RequestMapping({"/index", "/"})
    public ModelAndView showHomePage(@ModelAttribute("currentCity")
                                         final City city) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Movie> movies = movieService.getAllAvailable(city);
        Map<Boolean, List<Movie>> partitioned = movies.stream()
                .collect(Collectors.partitioningBy((movie) -> movie
                        .getScreeningDate()
                        .isBefore(LocalDate.now())));
        List<List<Movie>> inTheaters =
                Lists.partition(partitioned.get(Boolean.TRUE), MOVIE_ROW_SIZE);
        List<List<Movie>> comingSoon =
                Lists.partition(partitioned.get(Boolean.FALSE), MOVIE_ROW_SIZE);
        modelAndView.addObject("inTheaters", inTheaters);
        modelAndView.addObject("comingSoon", comingSoon);
        return modelAndView;
    }

    /**
     * Shows cinema page for admin.
     *
     * @return model and view
     */
    @RequestMapping("/admin/cinema")
    public ModelAndView showAdminCinemaPage() {
        return new ModelAndView("admin/cinema");
    }

    /**
     * Shows admin page for movie.
     *
     * @return model and view
     */
    @RequestMapping("/admin/movie")
    public ModelAndView showAdminMoviePage() {
        return new ModelAndView("admin/movie");
    }

    /**
     * Shows admin page for user.
     *
     * @return model and view
     */
    @RequestMapping("/admin/user")
    public ModelAndView showAdminUserPage() {
        return new ModelAndView("admin/user");
    }

    /**
     * Shows admin page for city.
     *
     * @return model and view
     */
    @RequestMapping("/admin/city")
    public ModelAndView showAdminCityPage() {
        return new ModelAndView("admin/city");
    }

    /**
     * Show admin page for session.
     *
     * @param cinemaId id of the cinema
     * @return model and view
     */
    @RequestMapping("/admin/cinema/{id}/session")
    public ModelAndView showAdminSessionPage(@PathVariable("id")
                                                 final UUID cinemaId) {
        ModelAndView modelAndView = new ModelAndView("admin/session");
        modelAndView.addObject("movies", movieService.getAll());
        modelAndView.addObject("halls", hallService.getAll(cinemaId));
        modelAndView.addObject("cinemaId", cinemaId);
        return modelAndView;
    }

    /**
     * Shows admin page for hall.
     *
     * @param cinemaId cinema's id
     * @return model and view
     */
    @RequestMapping("/admin/cinema/{id}/hall")
    public ModelAndView showAdminHallPage(@PathVariable("id")
                                             final UUID cinemaId) {
        ModelAndView modelAndView = new ModelAndView("admin/hall");
        Cinema cinema = cinemaService.get(cinemaId);
        modelAndView.addObject("halls", hallService.getAll(cinemaId));
        modelAndView.addObject("cinema", cinema);
        return modelAndView;
    }

    /**
     * Shows admin page for ticket.
     *
     * @param userId user's id
     * @return model and view
     */
    @RequestMapping("/admin/user/{id}/ticket")
    public ModelAndView showAdminTicketPage(@PathVariable("id")
                                             final UUID userId) {
        ModelAndView modelAndView = new ModelAndView("admin/ticket");
        modelAndView.addObject("tickets", ticketService.getAll(userId));
        modelAndView.addObject("user", userService.get(userId));
        return modelAndView;
    }
}
