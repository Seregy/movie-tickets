package movietickets.user.permission.web;


import movietickets.user.permission.Permission;
import movietickets.user.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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
     * Shows list of all permissions for admin page.
     *
     * @return model and view
     */
    @GetMapping("/admin/permissions")
    public ModelAndView showAdminPermissions() {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/permission_block");
        List<Permission> permissions = permissionService.getAll();
        modelAndView.addObject("permissions", permissions);
        return modelAndView;
    }

    /**
     * Adds new permission.
     *
     * @param name permission's name
     * @return response
     */
    @PostMapping("/permission")
    public ResponseEntity addPermission(@RequestParam("name")
                                        final String name) {
        permissionService.add(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing permission.
     *
     * @param id permission's id
     * @param name new name
     * @return response
     */
    @PostMapping("/permission/{id}")
    public ResponseEntity editPermission(@PathVariable("id")
                                   final UUID id,
                                   @RequestParam("name")
                                   final String name) {
        permissionService.changeName(id, name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes permission.
     *
     * @param id identifier of the permission
     * @return response code
     */
    @DeleteMapping("/permission/{id}")
    public ResponseEntity deletePermission(@PathVariable("id")
                                           final UUID id) {
        permissionService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
