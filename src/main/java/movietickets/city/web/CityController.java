package movietickets.city.web;

import movietickets.city.City;
import movietickets.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.UUID;

/**
 * City controller, used for changing current city.
 *
 * @author Seregy
 */
@Controller
@SessionAttributes("currentCity")
public class CityController {
    private CityService cityService;

    /**
     * Constructs new city controller.
     *
     * @param cityService city service
     */
    @Autowired
    public CityController(final CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Changes current city to the one
     * specified by id.
     *
     * @param id city's id
     * @param model spring's model
     * @return redirect to home page
     */
    @RequestMapping("/city/{id}")
    public String changeCity(@PathVariable("id") final String id,
                                final Model model) {
        City city = cityService.get(UUID.fromString(id));
        model.addAttribute("currentCity", city);
        return "redirect:/";
    }
}
