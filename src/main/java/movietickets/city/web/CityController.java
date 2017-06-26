package movietickets.city.web;

import movietickets.city.City;
import movietickets.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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
    @GetMapping("/city/{id}")
    public String changeCity(@PathVariable("id") final String id,
                                final Model model) {
        City city = cityService.get(UUID.fromString(id));
        model.addAttribute("currentCity", city);
        return "redirect:/";
    }

    /**
     * Shows list of all cities for admin page.
     *
     * @return model and view
     */
    @GetMapping("/admin/cities")
    public ModelAndView showAdminCities() {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/city_block");
        List<City> cities = cityService.getAll();
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    /**
     * Adds new city.
     *
     * @param name city name
     * @return response
     */
    @PostMapping("/city")
    public ResponseEntity addCity(@RequestParam("name")
                                   final String name) {
        City city = new City(name);
        cityService.add(city);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing city.
     *
     * @param id city's id
     * @param name new name
     * @return response
     */
    @PostMapping("/city/{id}")
    public ResponseEntity editCity(@PathVariable("id")
                                      final UUID id,
                                      @RequestParam("name")
                                      final String name) {
        cityService.changeName(id, name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes city.
     *
     * @param id city's id
     * @return response
     */
    @DeleteMapping("/city/{id}")
    public ResponseEntity deleteCity(@PathVariable("id") final UUID id) {
        cityService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
