package movietickets.user.role.dao;

import movietickets.user.role.Role;
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
 * Tests for Role data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class RoleDAOTest {
    @Autowired
    private RoleDAO roleDAO;

    @Test
    public void findRole() {
        Role[] roles = {
                new Role("First"),
                new Role("Second"),
                new Role("Third")};
        for (Role role : roles) {
            roleDAO.add(role);
        }

        assertEquals(roles[1], roleDAO.find(roles[1].getId()));
    }

    @Test
    public void findAllRoles() {
        Role[] roles = {
                new Role("First"),
                new Role("Second"),
                new Role("Third"),
                new Role("Fourth")};
        Arrays.sort(roles, Comparator.comparing(Role::getName));
        for (Role role : roles) {
            roleDAO.add(role);
        }
        assertArrayEquals(roles, roleDAO.findAll().toArray(new Role[0]));
    }

    @Test
    public void addRole() {
        Role role = new Role("Role name");

        assertTrue(roleDAO.add(role));
        assertEquals(role, roleDAO.find(role.getId()));
    }

    @Test
    public void updateRole() {
        Role role = new Role("Role name");
        roleDAO.add(role);
        role.setName("New role name");

        assertTrue(roleDAO.update(role));
        assertEquals(role.getName(),
                roleDAO.find(role.getId()).getName());
    }

    @Test
    public void deleteRole() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        assertTrue(roleDAO.delete(role.getId()));
        assertNull(roleDAO.find(role.getId()));
    }
}
