package movietickets.core.web;

import com.google.common.collect.Lists;
import movietickets.city.City;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import movietickets.ticket.Ticket;
import movietickets.user.CustomUserDetails;
import movietickets.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    /**
     * Constructs new app controller.
     *
     * @param movieService movie service
     * @param userService user service
     */
    @Autowired
    public AppController(final MovieService movieService,
                         final UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    /**
     * Shows main web-page.
     *
     * @param city current city
     * @return name of the view
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
     * @return name of jsp-page
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
}
