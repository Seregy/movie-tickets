package movietickets.user.role.controller;

import movietickets.user.role.Role;
import movietickets.user.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Seregy on 26.05.2017.
 */
@Controller
public class RoleController {
    private static Logger log =
            Logger.getLogger(RoleController.class.getName());

    private final RoleService roleService;

    /**
     * Constructs new user controller with given User Service.
     *
     * @param userService user service
     */
    @Autowired
    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Shows page with users.
     *
     * @return name of jsp-page
     */
    @GetMapping("/role")
    public String showRolesPage() {
        return "role";
    }

    /**
     * Shows table, filled with users.
     *
     * @return name of jsp-page
     */
    @GetMapping("/roles")
    public ModelAndView showRoles() {
        ModelAndView modelAndView = new ModelAndView("roles_table");
        modelAndView.addObject("roles", roleService.getAll());
        return modelAndView;
    }

    /**
     * Adds new user with given full name, user name, password,
     * salt and email.
     *
     * @param name full name of the user
     * @return response code
     */
    @PostMapping("/roles")
    public ResponseEntity addRole(@RequestParam("name")
                                  final String name) {
        roleService.add(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes user with given id.
     *
     * @param id identifier of the user
     * @return response code
     */
    @DeleteMapping("/roles/{id}")
    public ResponseEntity deleteRole(@PathVariable("id") final String id) {
        roleService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
