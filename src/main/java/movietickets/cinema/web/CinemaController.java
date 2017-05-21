package movietickets.cinema.web;

import movietickets.cinema.Cinema;
import movietickets.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Cinema controller, responsible for giving pages
 * and resources related to cinema.
 *
 * @author Seregy
 */
@Controller
public class CinemaController {
    private static Logger log =
            Logger.getLogger(CinemaController.class.getName());

    private final CinemaService cinemaService;

    /**
     * Constructs new cinema controller with given Cinema Service.
     *
     * @param cinemaService cinema service
     */
    @Autowired
    public CinemaController(final CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    /**
     * Shows page with cinemas.
     *
     * @return name of jsp-page
     */
    @GetMapping("/cinema")
    public String showCinemasPages() {
        return "cinema";
    }

    /**
     * Shows table, filled with cinemas.
     *
     * @return name of jsp-page
     */
    @GetMapping("/cinemas")
    public ModelAndView showCinemas() {
        ModelAndView modelAndView = new ModelAndView("cinemas_table");
        modelAndView.addObject("cinemas", cinemaService.getAll());
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
