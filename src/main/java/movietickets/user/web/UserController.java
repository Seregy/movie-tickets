package movietickets.user.web;

import movietickets.user.CustomUserDetails;
import movietickets.user.User;
import movietickets.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
     * Gets current user's details.
     *
     * @return user details
     */
    @ModelAttribute
    public CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    /**
     * Changes user's email.
     *
     * @param userDetails current user's details
     * @param email new email
     * @return http status
     */
    @PostMapping("/change_email")
    public ResponseEntity changeEmail(@ModelAttribute("customUserDetails")
                                      final CustomUserDetails userDetails,
                                      @RequestParam("email")
                                      final String email) {
        userService.changeEmail(userDetails.getId(), email);
        userDetails.setEmail(email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Changes user's password.
     *
     * @param userDetails current user's details
     * @param password new password
     * @return http status
     */
    @PostMapping("/change_password")
    public ResponseEntity changePassword(@ModelAttribute("customUserDetails")
                                         final CustomUserDetails userDetails,
                                         @RequestParam("password")
                                         final String password) {
        userService.changePassword(userDetails.getId(), password);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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

    @GetMapping("/admin/users")
    public ModelAndView showAdminUsers(@RequestParam(value = "search",
                                                        required = false)
                                            final String search) {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/user_block");
        List<User> users = userService.getAll();

        if (search != null) {
            List<User> result;
            result = users.stream()
                    .filter(u -> u.getName().contains(search))
                    .collect(Collectors.toList());

            if (result.isEmpty()) {
                result = users.stream()
                        .filter(u -> u.getEmail().contains(search))
                        .collect(Collectors.toList());
            }

            users = result;
        }

        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @PostMapping("/user/{id}")
    public ResponseEntity editUser(@PathVariable("id")
                                  final UUID id,
                                  @RequestParam("name")
                                  final String name,
                                  @RequestParam("password")final
                                  String password,
                                  @RequestParam("email")
                                  final String email) {
        userService.changeName(id, name);
        userService.changePassword(id, password);
        userService.changeEmail(id, email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes user with given id.
     *
     * @param id identifier of the user
     * @return response code
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") final String id) {
        userService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
