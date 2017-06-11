package movietickets.core.web;

import movietickets.city.City;
import movietickets.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Global controller, that adds models to all other controllers.
 *
 * @author Seregy
 */
@ControllerAdvice
public class GlobalModelsController {
    private final CityService cityService;

    /**
     * Constructs new global models controller.
     *
     * @param cityService city service
     */
    @Autowired
    public GlobalModelsController(final CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Sets cityList attribute.
     *
     * @return list of cities
     */
    @ModelAttribute
    public List<City> setCities() {
        return cityService.getAll();
    }

    /**
     * Sets currentCity attribute.
     *
     * @return new currentCity
     */
    @ModelAttribute("currentCity")
    public City setCurrentCity() {
        return cityService.getAll().stream()
                .filter(city -> city.getName().equals("Kyiv"))
                .findAny()
                .orElse(cityService.getAll().get(0));
    }
}
