package movietickets.user.role.web;

import movietickets.user.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

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
     * Adds new role with given name.
     *
     * @param name name of the role
     * @param permissionsIds permissions' identifiers
     * @return response code
     */
    @PostMapping("/roles")
    public ResponseEntity addRole(@RequestParam("name")
                                  final String name,
                                  @RequestParam("permissions_ids[]")
                                  final String[] permissionsIds) {
        UUID[] ids = Arrays.stream(permissionsIds)
                .map(UUID::fromString)
                .toArray(UUID[]::new);
        roleService.add(name, ids);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes role with given id.
     *
     * @param id identifier of the role
     * @return response code
     */
    @DeleteMapping("/roles/{id}")
    public ResponseEntity deleteRole(@PathVariable("id") final String id) {
        roleService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
