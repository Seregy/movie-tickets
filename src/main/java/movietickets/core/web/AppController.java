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
     * Shows admin web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/admin")
    public String showAdminPage() {
        return "admin/index";
    }

    /**
     * Shows main web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping({"/index", "/"})
    public String showHomePage() {
        return "index";
    }

    /**
     * Shows cinema web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/cinema")
    public String showCinemaPage() {
        return "cinema";
    }

    /**
     * Shows web-page with list of cinemas.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/cinemas")
    public String showCinemaListPage() {
        return "cinema_list";
    }

    /**
     * Shows film web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/film")
    public String showFilmPage() {
        return "film";
    }

    /**
     * Shows profile web-page.
     *
     * @return name of jsp-page
     */
    @RequestMapping("/m/profile")
    public String showProfilePage() {
        return "profile";
    }
}
