package movietickets.user.web;

import movietickets.user.User;
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
        return "user";
    }

    /**
     * Shows table, filled with users.
     *
     * @return name of jsp-page
     */
    @GetMapping("/users")
    public ModelAndView showUsers() {
        ModelAndView modelAndView = new ModelAndView("users_table");
        modelAndView.addObject("users", userService.getAll());
        return modelAndView;
    }

    /**
     * Adds new user with given full name, user name, password,
     * salt and email.
     *
     * @param fullName full name of the user
     * @param userName user's name
     * @param password user's password
     * @param salt password's salt
     * @param email user's email
     * @return response code
     */
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestParam("full_name")
                                    final String fullName,
                                  @RequestParam("user_name")
                                    final String userName,
                                  @RequestParam("password")final
                                    String password,
                                  @RequestParam("password_salt")
                                    final String salt,
                                  @RequestParam("email")
                                    final String email) {
        User user = new User(fullName, userName, password, salt, email);
        userService.register(user);
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
