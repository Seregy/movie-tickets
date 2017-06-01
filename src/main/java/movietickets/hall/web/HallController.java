package movietickets.hall.web;

import movietickets.hall.Hall;
import movietickets.hall.layout.Layout;
import movietickets.hall.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private final HallService hallService;

    /**
     * Constructs new hall controller with given Hall Service.
     *
     * @param hallService hall service
     */
    @Autowired
    public HallController(final HallService hallService) {
        this.hallService = hallService;
    }


    /**
     * Shows main halls' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/hall")
    public String showHallsPages() {
        return "admin/hall";
    }

    /**
     * Shows table, filled with halls.
     *
     * @return name of jsp-page
     */
    @GetMapping("/halls")
    public ModelAndView showHalls() {
        ModelAndView modelAndView = new ModelAndView("admin/halls_table");
        modelAndView.addObject("halls", hallService.getAll());
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
        hallService.add(hall, layout, UUID.fromString(cinemaId));
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
        hallService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
