package movietickets.user.web;

import movietickets.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * User controller, responsible for giving pages and resources related to user.
 *
 * @author Seregy
 */
@Controller
public class UserController {
    private static Logger log =
            Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    /**
     * Constructs new user controller with given User Service.
     *
     * @param userService user service
     */
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Shows page with users.
     *
     * @return name of jsp-page
     */
    @GetMapping("/user")
    public String showUsersPage() {
        return "admin/user";
    }

    /**
     * Shows table, filled with users.
     *
     * @return name of jsp-page
     */
    @GetMapping("/users")
    public ModelAndView showUsers() {
        ModelAndView modelAndView = new ModelAndView("admin/users_table");
        modelAndView.addObject("users", userService.getAll());
        return modelAndView;
    }

    /**
     * Adds new user.
     *
     * @param name user's name
     * @param password user's password
     * @param roleId user's role identifier
     * @param email user's email
     * @return response code
     */
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestParam("name")
                                    final String name,
                                  @RequestParam("password")final
                                    String password,
                                  @RequestParam("role_id")
                                    final String roleId,
                                  @RequestParam("email")
                                    final String email) {
        userService.register(name, password, UUID.fromString(roleId),
                email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes user with given id.
     *
     * @param id identifier of the user
     * @return response code
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") final String id) {
        userService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
