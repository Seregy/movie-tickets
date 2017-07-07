package movietickets.user.permission.dao;

import movietickets.user.permission.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Tests for Permission data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class PermissionDAOTest {
    @Autowired
    private PermissionDAO permissionDAO;

    @Test
    public void findPermission() {
        Permission[] permissions = {
                new Permission("PM_1"),
                new Permission("PM_2"),
                new Permission("PM_3")};
        for (Permission permission : permissions) {
            permissionDAO.add(permission);
        }

        assertEquals(permissions[1], permissionDAO.find(permissions[1].getId()));
    }

    @Test
    public void findAllPermissions() {
        Permission[] permissions = {
                new Permission("PM_1"),
                new Permission("PM_2"),
                new Permission("PM_3"),
                new Permission("PM_4")};
        Arrays.sort(permissions, Comparator.comparing(Permission::getName));
        for (Permission permission : permissions) {
            permissionDAO.add(permission);
        }

        assertArrayEquals(permissions, permissionDAO.findAll().toArray(new Permission[0]));
    }

    @Test
    public void addPermission() {
        Permission permission = new Permission("PM_00");

        assertTrue(permissionDAO.add(permission));
        assertEquals(permission, permissionDAO.find(permission.getId()));
    }

    @Test
    public void updatePermission() {
        Permission permission = new Permission("PM_00");
        permissionDAO.add(permission);
        permission.setName("PM_11");

        assertTrue(permissionDAO.update(permission));
        assertEquals(permission.getName(),
                permissionDAO.find(permission.getId()).getName());
    }

    @Test
    public void deletePermission() {
        Permission permission = new Permission("PM_00");
        permissionDAO.add(permission);

        assertTrue(permissionDAO.delete(permission.getId()));
        assertNull(permissionDAO.find(permission.getId()));
    }
}
