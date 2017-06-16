package movietickets.core.web;

import com.google.common.collect.Lists;
import movietickets.city.City;
import movietickets.hall.service.HallService;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import movietickets.ticket.Ticket;
import movietickets.user.CustomUserDetails;
import movietickets.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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

    /**
     * Constructs new app controller.
     *
     * @param movieService movie service
     * @param userService user service
     * @param hallService hall service
     */
    @Autowired
    public AppController(final MovieService movieService,
                         final UserService userService,
                         final HallService hallService) {
        this.movieService = movieService;
        this.userService = userService;
        this.hallService = hallService;
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
     * Shows profile web-page.
     *
     * @return model and view
     */
    @RequestMapping("/profile")
    public ModelAndView showProfilePage() {
        ModelAndView modelAndView = new ModelAndView("profile");
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        modelAndView.addObject("user", userDetails);
        List<Ticket> tickets = userService.getTickets(userDetails.getId());
        modelAndView.addObject("tickets", tickets);
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
}
