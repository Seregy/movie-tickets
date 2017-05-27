package movietickets.user.permission.web;


import movietickets.user.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Permission controller, responsible for giving pages
 * and resources related to permission.
 *
 * @author Seregy
 */
@Controller
public class PermissionController {
    private static Logger log =
            Logger.getLogger(PermissionController.class.getName());

    private final PermissionService permissionService;

    /**
     * Constructs new permission controller.
     *
     * @param permissionService permission service
     */
    @Autowired
    public PermissionController(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * Shows page with permissions.
     *
     * @return name of jsp-page
     */
    @GetMapping("/permission")
    public String showPermissionsPage() {
        return "permission";
    }

    /**
     * Shows table, filled with permissions.
     *
     * @return name of jsp-page
     */
    @GetMapping("/permissions")
    public ModelAndView showPermissions() {
        ModelAndView modelAndView = new ModelAndView("permissions_table");
        modelAndView.addObject("permissions", permissionService.getAll());
        return modelAndView;
    }

    /**
     * Adds new permission with given name.
     *
     * @param name permission's name
     * @return response code
     */
    @PostMapping("/permissions")
    public ResponseEntity addPermission(@RequestParam("name")
                                  final String name) {
        permissionService.add(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes permission with given id.
     *
     * @param id identifier of the permission
     * @return response code
     */
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity deletePermission(@PathVariable("id")
                                               final String id) {
        permissionService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
