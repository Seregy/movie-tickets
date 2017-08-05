package movietickets.user.role.web;

import movietickets.user.role.Role;
import movietickets.user.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Role controller, responsible for giving pages and resources related to role.
 *
 * @author Seregy
 */
@Controller
public class RoleController {
    private static Logger log =
            Logger.getLogger(RoleController.class.getName());

    private final RoleService roleService;

    /**
     * Constructs new role controller.
     *
     * @param roleService role service
     */
    @Autowired
    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Shows list of all roles for admin page.
     *
     * @return model and view
     */
    @GetMapping("/admin/roles")
    public ModelAndView showAdminRoles() {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/role_block");
        List<Role> roles = roleService.getAll();

        Map<Role, List<String>> map = new HashMap<>();
        roles.forEach(r -> map.put(r,
                roleService.getPermissions(r.getId()).stream()
                        .map(p -> p.getId().toString())
                        .collect(Collectors.toList())));
        modelAndView.addObject("rolePermissionMap", map);
        return modelAndView;
    }

    /**
     * Adds new role.
     *
     * @param name role's name
     * @param permissions permissions' identifiers
     * @return response
     */
    @PostMapping("/role")
    public ResponseEntity addRole(@RequestParam("name")
                                  final String name,
                                  @RequestParam(name = "permissions[]",
                                          required = false)
                                  final UUID[] permissions) {
        UUID[] uuids = new UUID[0];
        if (permissions != null) {
            uuids = permissions;
        }
        roleService.add(name, uuids);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing city.
     *
     * @param id role's id
     * @param name new name
     * @param permissions new permissions' ids
     * @return response
     */
    @PostMapping("/role/{id}")
    public ResponseEntity editRole(@PathVariable("id")
                                   final UUID id,
                                   @RequestParam("name")
                                   final String name,
                                   @RequestParam(name = "permissions[]",
                                           required = false)
                                   final UUID[] permissions) {
        roleService.changeName(id, name);
        UUID[] uuids = new UUID[0];
        if (permissions != null) {
            uuids = permissions;
        }
        roleService.changePermissions(id, uuids);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes role.
     *
     * @param id role's id
     * @return response
     */
    @DeleteMapping("/role/{id}")
    public ResponseEntity deleteRole(@PathVariable("id") final UUID id) {
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
