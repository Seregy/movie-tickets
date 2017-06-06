package movietickets.core.web;

import com.google.common.collect.Lists;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class AppController {
    /**
     * Number of movies, displayed in one row on the page.
     */
    public static final int MOVIE_ROW_SIZE = 6;
    private static Logger log = Logger.getLogger(AppController.class.getName());
    private final MovieService movieService;

    /**
     * Constructs new app controller.
     *
     * @param movieService movie service
     */
    @Autowired
    public AppController(final MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Shows main web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping({"/index", "/"})
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Movie> movies = movieService.getAll();
        movies.sort(Comparator.comparing(Movie::getScreeningDate));
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
     * Shows admin web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/admin")
    public String showAdminPage() {
        return "admin/index";
    }

    /**
     * Shows cinema web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/cinema")
    public String showCinemaPage() {
        return "cinema";
    }

    /**
     * Shows web-page with list of cinemas.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/cinemas")
    public String showCinemaListPage() {
        return "cinema_list";
    }

    /**
     * Shows film web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/film")
    public String showFilmPage() {
        return "movie";
    }

    /**
     * Shows profile web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/profile")
    public String showProfilePage() {
        return "profile";
    }
}
