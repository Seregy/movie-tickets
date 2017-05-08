package movietickets.cinema.web;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Cinema controller, responsible for giving pages
 * and resources related to cinema.
 *
 * @author Seregy
 */
@Controller
@Transactional
public class CinemaController {
    private static Logger log =
            Logger.getLogger(CinemaController.class.getName());

    private final CinemaDAO cinemaDAO;
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Constructs new cinema controller with given Cinema DAO.
     *
     * @param cinemaDAO cinema data access object
     */
    @Autowired
    public CinemaController(final CinemaDAO cinemaDAO) {
        this.cinemaDAO = cinemaDAO;
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
        modelAndView.addObject("cinemas", cinemaDAO.findAll());
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
        cinemaDAO.add(cinema);
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
        cinemaDAO.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
