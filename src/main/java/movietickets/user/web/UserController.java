package movietickets.user.web;

import movietickets.ticket.Ticket;
import movietickets.user.CustomUserDetails;
import movietickets.user.User;
import movietickets.user.role.Role;
import movietickets.user.role.service.RoleService;
import movietickets.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@SessionAttributes("currentCity")
public class UserController {
    private static final String DEFAULT_USER_ROLE = "User";
    private static Logger log =
            Logger.getLogger(UserController.class.getName());

    private final UserService userService;
    private final RoleService roleService;

    /**
     * Constructs new user controller with given User Service.
     *
     * @param userService user service
     */
    @Autowired
    public UserController(final UserService userService,
                          final RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Gets current user's details.
     *
     * @return user details
     */
    @ModelAttribute
    public CustomUserDetails getUserDetails() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        } else {
            return null;
        }
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/login-error")
    public ModelAndView showLoginError() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginError", true);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationPage() {
        return new ModelAndView("register");
    }

    /**
     * Registers new user.
     *
     * @param name user's name
     * @param password user's password
     * @param email user's email
     * @return response code
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam("username")
                                    final String name,
                                  @RequestParam("password")final
                                    String password,
                                  @RequestParam("email")
                                    final String email) {
        Role role = roleService.getAll().stream()
                .filter(r -> r.getName().contains(DEFAULT_USER_ROLE))
                .findAny()
                .orElse(null);
        userService.register(name, password, role.getId(), email);
        return "redirect:/profile";
    }

    /**
     * Shows profile web-page.
     *
     * @return model and view
     */
    @RequestMapping("/profile")
    public ModelAndView showProfilePage() {
        ModelAndView modelAndView = new ModelAndView("profile");
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        modelAndView.addObject("user", userDetails);
        List<Ticket> tickets = userService.getTickets(userDetails.getId());
        modelAndView.addObject("tickets", tickets);
        return modelAndView;
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
