package movietickets.user.permission.service;

import movietickets.core.WithMockCustomUser;
import movietickets.user.permission.Permission;
import movietickets.user.permission.dao.PermissionDAO;
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

import static org.junit.Assert.*;

/**
 * Tests for {@link PermissionServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PermissionServiceDAOTest {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionDAO permissionDAO;

    private void addPermission() {
        assertTrue(permissionDAO.findAll().isEmpty());

        permissionService.add("permission");

        assertEquals(1, permissionDAO.findAll().size());
        assertEquals("permission", permissionDAO.findAll().get(0).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addPermissionUnauthenticated() {
        addPermission();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addPermissionWithoutPermission() {
        addPermission();
    }

    @WithMockCustomUser(authorities = "PERMISSION_ADD_ALL")
    @Test
    public void addPermissionWithPermission() {
        addPermission();
    }

    @Test
    public void getPermission() {
        createPermission(UUID.randomUUID());
        Permission permission2 = createPermission(UUID.randomUUID());

        assertEquals(permission2, permissionService.get(permission2.getId()));
    }

    @Test
    public void getAllPermissions() {
        createPermission(UUID.randomUUID());
        createPermission(UUID.randomUUID());

        List<Permission> permissions = permissionService.getAll();
        assertEquals(2, permissions.size());
    }

    private void deletePermission() {
        Permission permission = createPermission(UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertFalse(permissionDAO.findAll().isEmpty());
        permissionService.delete(permission.getId());
        assertTrue(permissionDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deletePermissionUnauthenticated() {
        deletePermission();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deletePermissionWithoutPermission() {
        deletePermission();
    }

    @WithMockCustomUser(authorities = {"PERMISSION_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deletePermissionWithSpecificPermission() {
        deletePermission();
    }

    @WithMockCustomUser(authorities = {"PERMISSION_DELETE_ALL"})
    @Test
    public void deletePermissionWithGlobalPermission() {
        deletePermission();
    }

    private void changeName() {
        Permission permission = createPermission(UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("permission" + permission.getId(), permissionDAO.find(permission.getId()).getName());
        permissionService.changeName(permission.getId(), "newName");
        assertEquals("newName", permissionDAO.find(permission.getId()).getName());
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

    @WithMockCustomUser(authorities = {"PERMISSION_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeNameWithSpecificPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"PERMISSION_EDIT_ALL"})
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
}
