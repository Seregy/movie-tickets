package movietickets.hall.web;

import movietickets.cinema.dao.CinemaDAO;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import movietickets.hall.layout.dao.LayoutDAO;
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

/**
 * Hall controller, responsible for giving pages and resources related to hall.
 *
 * @author Seregy
 */
@Controller
@Transactional
public class HallController {

    private final HallDAO hallDAO;
    private final CinemaDAO cinemaDAO;
    private final LayoutDAO layoutDAO;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs new hall controller with given Hall DAO,
     * Cinema DAO and Layout DAO.
     *
     * @param hallDAO hall data access object
     * @param cinemaDAO cinema data access object
     * @param layoutDAO layout data access object
     */
    @Autowired
    public HallController(final HallDAO hallDAO,
                          final CinemaDAO cinemaDAO,
                          final LayoutDAO layoutDAO) {
        this.hallDAO = hallDAO;
        this.cinemaDAO = cinemaDAO;
        this.layoutDAO = layoutDAO;
    }


    /**
     * Shows main halls' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/hall")
    public String showHallsPages() {
        return "hall";
    }

    /**
     * Shows table, filled with halls.
     *
     * @return name of jsp-page
     */
    @GetMapping("/halls")
    public ModelAndView showHalls() {
        ModelAndView modelAndView = new ModelAndView("halls_table");
        modelAndView.addObject("halls", hallDAO.findAll());
        return modelAndView;
    }

    /**
     * Adds new hall with given name.
     *
     * @param name hall name
     * @param cinemaId cinema identifier
     * @param rowsAmount amount of rows in the hall
     * @param seatsAmount amount of seats per row in the hall
     * @return response code
     */
    @PostMapping("/halls")
    public ResponseEntity addHall(@RequestParam("name")
                                  final String name,
                                  @RequestParam("cinema_id")
                                  final String cinemaId,
                                  @RequestParam("rows_amount")
                                  final int rowsAmount,
                                  @RequestParam("seats_amount")
                                  final int seatsAmount) {
        Hall hall = new Hall(name);
        Layout layout = new Layout(rowsAmount, seatsAmount);
        hall.setLayout(layout);
        layoutDAO.add(layout);
        hallDAO.add(hall);
        cinemaDAO.find(UUID.fromString(cinemaId)).addHall(hall);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes hall with given id.
     *
     * @param id identifier of the hall
     * @return response code
     */
    @DeleteMapping("/halls/{id}")
    public ResponseEntity deleteHall(@PathVariable("id") final String id) {
        hallDAO.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
