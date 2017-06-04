package movietickets.user.dao;

import movietickets.session.Session;
import movietickets.user.User;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Tests for User data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.TestConfiguration.class)
@Transactional
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    @Test
    public void findUser() {
        Role[] roles = {
                new Role("First"),
                new Role("Second"),
                new Role("Third")};
        for (Role role : roles) {
            roleDAO.add(role);
        }

        User[] users = {
                new User("first", "first", roles[0], "first"),
                new User("second", "second", roles[1], "second"),
                new User("third", "third", roles[2], "third")};
        for (User user : users) {
            userDAO.add(user);
        }

        assertEquals(users[1], userDAO.find(users[1].getId()));
    }

    @Test
    public void findAllUsers() {
        Role[] roles = {
                new Role("First"),
                new Role("Second"),
                new Role("Third"),
                new Role("Fourth")};
        for (Role role : roles) {
            roleDAO.add(role);
        }

        User[] users = {
                new User("first", "first", roles[0], "first"),
                new User("second", "second", roles[1], "second"),
                new User("third", "third", roles[2], "third"),
                new User("fourth", "fourth", roles[3], "fourth")};
        for (User user : users) {
            userDAO.add(user);
        }

        assertArrayEquals(users, userDAO.findAll().toArray(new User[0]));
    }

    @Test
    public void addUser() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        User user = new User("Name", "Password", role, "Email");

        assertTrue(userDAO.add(user));
        assertEquals(user, userDAO.find(user.getId()));
    }

    @Test
    public void updateUser() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        User user = new User("Name", "Password", role, "Email");
        userDAO.add(user);
        user.setName("New name");

        assertTrue(userDAO.update(user));
        assertEquals(user.getName(),
                userDAO.find(user.getId()).getName());
    }

    @Test
    public void deleteUser() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        User user = new User("Name", "Password", role, "Email");
        userDAO.add(user);

        assertTrue(userDAO.delete(user.getId()));
        assertNull(userDAO.find(user.getId()));
    }
}
