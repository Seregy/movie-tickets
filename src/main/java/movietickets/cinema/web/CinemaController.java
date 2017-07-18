package movietickets.cinema.web;

import com.google.common.collect.Lists;
import movietickets.cinema.Cinema;
import movietickets.cinema.service.CinemaService;
import movietickets.city.City;
import movietickets.city.service.CityService;
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
    private final CityService cityService;

    /**
     * Constructs new cinema controller.
     *
     * @param cinemaService cinema service
     * @param movieService movie service
     * @param cityService city service
     */
    @Autowired
    public CinemaController(final CinemaService cinemaService,
                            final MovieService movieService,
                            final CityService cityService) {
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.cityService = cityService;
    }

    /**
     * Shows cinema page and movies that
     * are shown in this cinema.
     *
     * @param id cinema's id
     * @return model and view
     */
    @GetMapping("/cinema/{id}")
    public ModelAndView showCinemaPages(@PathVariable("id") final UUID id) {
        ModelAndView modelAndView = new ModelAndView("cinema");
        modelAndView.addObject("cinema",
                cinemaService.get(id));
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
     * Shows list of all cinemas for admin panel.
     *
     * @param name cinema's name filter
     * @return name the view
     */
    @GetMapping("/admin/cinemas")
    public ModelAndView showAdminCinemas(@RequestParam(value = "name",
                                                        required = false)
                                             final String name) {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/cinema_block");
        List<Cinema> cinemas = cinemaService.getAll();
        if (name != null) {
            cinemas = cinemas.stream()
                    .filter(c -> c.getName().contains(name))
                    .collect(Collectors.toList());
        }

        modelAndView.addObject("cinemas", cinemas);
        return modelAndView;
    }

    /**
     * Adds new cinema.
     *
     * @param name name of the cinema
     * @param city cinema's city name
     * @param address cinema's address
     * @param phone cinema's phone number
     * @param website cinema's website
     * @return response code
     */
    @PostMapping("/cinema")
    public ResponseEntity addCinema(@RequestParam("name")
                                        final String name,
                                    @RequestParam("city")
                                        final String city,
                                    @RequestParam("address")
                                        final String address,
                                    @RequestParam("phone")
                                        final String phone,
                                    @RequestParam("website")
                                        final String website) {
        Cinema cinema = new Cinema(name, address, phone, website);
        UUID cityId = cityService.getAll().stream()
                .filter(c -> c.getName().equals(city))
                .findAny()
                .get().getId();
        cinemaService.add(cinema, cityId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing cinema.
     *
     * @param id cinema's id
     * @param name cinema's name
     * @param city city's name
     * @param address cinema's address
     * @param phone cinema's phone
     * @param website cinema's website
     * @return response entity
     */
    @PostMapping("/cinema/{id}")
    public ResponseEntity editCinema(@PathVariable("id")
                                        final UUID id,
                                     @RequestParam("name")
                                        final String name,
                                     @RequestParam("city")
                                        final String city,
                                     @RequestParam("address")
                                        final String address,
                                     @RequestParam("phone")
                                        final String phone,
                                     @RequestParam("website")
                                        final String website) {
        UUID cityId = cityService.getAll().stream()
                .filter(c -> c.getName().equals(city))
                .findAny()
                .get().getId();
        cinemaService.changeName(id, name);
        cinemaService.changeCity(id, cityId);
        cinemaService.changeAddress(id, address);
        cinemaService.changePhone(id, phone);
        cinemaService.changeWebsite(id, website);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes cinema with given id.
     *
     * @param id identifier of the cinema
     * @return response code
     */
    @DeleteMapping("/cinema/{id}")
    public ResponseEntity deleteCinema(@PathVariable("id") final UUID id) {
        cinemaService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
