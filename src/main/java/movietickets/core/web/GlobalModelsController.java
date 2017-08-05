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
     * @param mockCity mock city to be used instead of the real one
     * @return new currentCity
     */
    @Autowired
    @ModelAttribute("currentCity")
    public City setCurrentCity(final City mockCity) {
        return cityService.getAll().stream()
                .findFirst()
                .orElse(mockCity);
    }
}
