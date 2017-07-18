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

import java.util.List;
import java.util.UUID;

/**
 * Hall controller, responsible for giving pages and resources related to hall.
 *
 * @author Seregy
 */
@Controller
@SessionAttributes("currentCity")
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
     * Shows list of all hall of the cinema
     * for admin page.
     *
     * @param cinemaId cinema's id
     * @return model and view
     */
    @GetMapping("/admin/cinema/{id}/halls")
    public ModelAndView showAdminHalls(@PathVariable("id")
                                           final UUID cinemaId) {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/hall_block");
        List<Hall> halls = hallService.getAll(cinemaId);
        modelAndView.addObject("halls", halls);
        return modelAndView;
    }

    /**
     * Adds new hall.
     * Hall's new layout is an array with each element
     * representing hall's row. Elements consists of comma-separated names
     * of seat types.
     *
     * @param name hall name
     * @param cinemaId cinema identifier
     * @param newLayout new hall layout
     * @return response
     */
    @PostMapping("/hall")
    public ResponseEntity addHall(@RequestParam("name")
                                  final String name,
                                  @RequestParam("cinema")
                                  final String cinemaId,
                                  @RequestParam("seats[]")
                                  final String[] newLayout) {
        String[][] array
                = new String[newLayout.length][newLayout[0].split(",").length];

        for (int i = 0; i < newLayout.length; i++) {
            array[i] = newLayout[i].split(",");
        }
        Hall hall = new Hall(name);
        Layout layout = new Layout(array);
        hallService.add(hall, layout, UUID.fromString(cinemaId));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes hall with given id.
     *
     * @param id identifier of the hall
     * @return response
     */
    @DeleteMapping("/hall/{id}")
    public ResponseEntity deleteHall(@PathVariable("id") final UUID id) {
        hallService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
