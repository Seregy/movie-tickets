package movietickets.cinema.web;

import com.google.common.collect.Lists;
import movietickets.cinema.Cinema;
import movietickets.cinema.service.CinemaService;
import movietickets.city.City;
import movietickets.core.web.AppController;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Cinema controller, responsible for giving pages
 * and resources related to cinema.
 *
 * @author Seregy
 */
@SessionAttributes("currentCity")
@Controller
public class CinemaController {
    private static Logger log =
            Logger.getLogger(CinemaController.class.getName());

    private final CinemaService cinemaService;
    private final MovieService movieService;

    /**
     * Constructs new cinema controller.
     *
     * @param cinemaService cinema service
     * @param movieService movie service
     */
    @Autowired
    public CinemaController(final CinemaService cinemaService,
                            final MovieService movieService) {
        this.cinemaService = cinemaService;
        this.movieService = movieService;
    }

    /**
     * Shows cinema page and movies that
     * are shown in this cinema.
     *
     * @param id cinema's id
     * @return model and view
     */
    @GetMapping("/cinema/{id}")
    public ModelAndView showCinemaPages(@PathVariable("id") final String id) {
        ModelAndView modelAndView = new ModelAndView("cinema");
        modelAndView.addObject("cinema",
                cinemaService.get(UUID.fromString(id)));
        List<Movie> movies = movieService.getAllAvailable();
        Map<Boolean, List<Movie>> partitioned = movies.stream()
                .collect(Collectors.partitioningBy((movie) -> movie
                        .getScreeningDate()
                        .isBefore(LocalDate.now())));
        List<List<Movie>> inTheaters =
                Lists.partition(partitioned.get(Boolean.TRUE),
                        AppController.MOVIE_ROW_SIZE);
        List<List<Movie>> comingSoon =
                Lists.partition(partitioned.get(Boolean.FALSE),
                        AppController.MOVIE_ROW_SIZE);
        modelAndView.addObject("inTheaters", inTheaters);
        modelAndView.addObject("comingSoon", comingSoon);
        return modelAndView;
    }

    /**
     * Shows list of cinemas in the current city.
     *
     * @param city current city
     * @return name the view
     */
    @GetMapping("/cinemas")
    public ModelAndView showCinemas(@ModelAttribute("currentCity")
                                        final City city) {
        ModelAndView modelAndView = new ModelAndView("cinema_list");
        modelAndView.addObject("cinemas", cinemaService.getAll(city));
        return modelAndView;
    }

    /**
     * Adds new cinema with given name and location.
     *
     * @param name name of the cinema
     * @param location location of the cinema
     * @return response code
     */
    @PostMapping("/cinemas")
    public ResponseEntity addCinema(@RequestParam("name")
                                        final String name,
                                    @RequestParam("location")
                                        final String location) {
        Cinema cinema = new Cinema(name, location);
        cinemaService.add(cinema);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes cinema with given id.
     *
     * @param id identifier of the cinema
     * @return response code
     */
    @DeleteMapping("/cinemas/{id}")
    public ResponseEntity deleteCinema(@PathVariable("id") final String id) {
        cinemaService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
