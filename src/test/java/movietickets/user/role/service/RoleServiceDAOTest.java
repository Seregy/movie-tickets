package movietickets.user.role.service;

import movietickets.core.WithMockCustomUser;
import movietickets.user.permission.Permission;
import movietickets.user.permission.dao.PermissionDAO;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link RoleServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoleServiceDAOTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PermissionDAO permissionDAO;

    private void addRole() {
        assertTrue(roleDAO.findAll().isEmpty());

        roleService.add("role");

        assertEquals(1, roleDAO.findAll().size());
        assertEquals("role", roleDAO.findAll().get(0).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addRoleUnauthenticated() {
        addRole();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addRoleWithoutPermission() {
        addRole();
    }

    @WithMockCustomUser(authorities = "ROLE_ADD_ALL")
    @Test
    public void addRoleWithPermission() {
        addRole();
    }

    private void addRoleWithDefinedPermissions() {
        assertTrue(roleDAO.findAll().isEmpty());

        Permission permission1 = createPermission(UUID.randomUUID());
        Permission permission2 = createPermission(UUID.randomUUID());
        roleService.add("role", permission1.getId(), permission2.getId());

        assertEquals(1, roleDAO.findAll().size());
        Role role = roleDAO.findAll().get(0);
        assertEquals("role", role.getName());
        assertTrue(role.getPermissions().contains(permission1));
        assertTrue(role.getPermissions().contains(permission2));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addRoleWithDefinedPermissionsUnauthenticated() {
        addRoleWithDefinedPermissions();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addRoleWithDefinedPermissionsWithoutPermission() {
        addRoleWithDefinedPermissions();
    }

    @WithMockCustomUser(authorities = "ROLE_ADD_ALL")
    @Test
    public void addRoleWithDefinedPermissionsWithPermission() {
        addRoleWithDefinedPermissions();
    }

    @Test
    public void getRole() {
        createRole(UUID.randomUUID());
        Role role2 = createRole(UUID.randomUUID());

        assertEquals(role2, roleService.get(role2.getId()));
    }

    @Test
    public void getAllPermissions() {
        createRole(UUID.randomUUID());
        createRole(UUID.randomUUID());

        List<Role> roles = roleService.getAll();
        assertEquals(2, roles.size());
    }

    private void deleteRole() {
        Role role = createRole(UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertFalse(roleDAO.findAll().isEmpty());
        roleService.delete(role.getId());
        assertTrue(roleDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteRoleUnauthenticated() {
        deleteRole();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteRoleWithoutPermission() {
        deleteRole();
    }

    @WithMockCustomUser(authorities = {"ROLE_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteRoleWithSpecificPermission() {
        deleteRole();
    }

    @WithMockCustomUser(authorities = {"ROLE_DELETE_ALL"})
    @Test
    public void deleteRoleWithGlobalPermission() {
        deleteRole();
    }

    private void changeName() {
        Role role = createRole(UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("role" + role.getId(), roleDAO.find(role.getId()).getName());
        roleService.changeName(role.getId(), "newName");
        assertEquals("newName", roleDAO.find(role.getId()).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeNameUnauthenticated() {
        changeName();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeNameWithoutPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"ROLE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeNameWithSpecificPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"ROLE_EDIT_ALL"})
    @Test
    public void changeNameWithGlobalPermission() {
        changeName();
    }

    private Permission createPermission(final UUID id) {
        Permission permission = new Permission("permission" + id.toString());
        permission.setId(id);
        permissionDAO.add(permission);
        return permission;
    }
    
    private Role createRole(final UUID id) {
        Role role = new Role("role" + id.toString());
        role.setId(id);
        roleDAO.add(role);
        return role;
    }
}
