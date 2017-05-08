package movietickets.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * Main app controller, responsible for giving general pages/resources.
 *
 * @author Seregy
 */
@Controller
public class AppController {
    private static Logger log = Logger.getLogger(AppController.class.getName());

    /**
     * Shows application's main web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }
}
