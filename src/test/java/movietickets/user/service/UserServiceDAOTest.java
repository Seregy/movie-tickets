package movietickets.user.service;

import movietickets.hall.layout.SeatType;
import movietickets.seat.Seat;
import movietickets.ticket.Ticket;
import movietickets.user.User;
import movietickets.core.WithMockCustomUser;
import movietickets.user.dao.UserDAO;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
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
 * Tests for UserServiceDAO.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceDAOTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Autowired
    private UserService userService;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void registerUser() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        userService.register("user",
                "user",
                role.getId(),
                "user@mail.com");
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getAllUsersUnauthenticated() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        addUser("user", "user", role, "user@mail.com",
                UUID.fromString("10000000-0000-0000-0000-000000000000"));
        addUser("manager", "manager", role, "manager@mail.com",
                UUID.fromString("20000000-0000-0000-0000-000000000000"));

        userService.getAll().isEmpty();
    }

    @WithMockCustomUser
    @Test
    public void getAllUsersWithoutPermission() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        addUser("user", "user", role, "user@mail.com",
                UUID.fromString("10000000-0000-0000-0000-000000000000"));
        addUser("manager", "manager", role, "manager@mail.com",
                UUID.fromString("20000000-0000-0000-0000-000000000000"));

        assertTrue(userService.getAll().isEmpty());
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void getAllUsersAsOneOfOwners() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        addUser("user", "user", role, "user@mail.com",
                UUID.fromString("10000000-0000-0000-0000-000000000000"));
        addUser("manager", "manager", role, "manager@mail.com",
                UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<User> users = userService.getAll();
        assertTrue(users.size() == 1);
        assertTrue(users.get(0).getName().equals("user"));
    }

    @WithMockCustomUser(authorities = "USER_READ_ALL")
    @Test
    public void getAllUsersWithPermission() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        addUser("user", "user", role, "user@mail.com",
                UUID.fromString("10000000-0000-0000-0000-000000000000"));
        addUser("manager", "manager", role, "manager@mail.com",
                UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<User> users = userService.getAll();
        assertTrue(users.size() == 2);
    }

    private void getUserTickets() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        User user = addUser("user", "user", role, "user@mail.com",
                UUID.fromString("10000000-0000-0000-0000-000000000000"));
        user.addTicket(new Ticket(new Seat(1, 1, SeatType.REGULAR, 1), user));
        user.addTicket(new Ticket(new Seat(2, 2, SeatType.REGULAR, 2), user));
        userDAO.update(user);

        List<Ticket> tickets = userService.getTickets(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        assertTrue(tickets.size() == 2);
        assertTrue(tickets.get(0).getUser().getName().equals("user"));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getUserTicketsUnauthenticated() {
        getUserTickets();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void getUserTicketsWithoutPermission() {
        getUserTickets();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void getUserTicketsAsOwner() {
        getUserTickets();
    }

    @WithMockCustomUser(authorities = "USER_READ_ALL")
    @Test
    public void getUserTicketsWithPermission() {
        getUserTickets();
    }

    private void deleteUser() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        UUID id = UUID.fromString("10000000-0000-0000-0000-000000000000");
        addUser("user", "user", role, "user@mail.com", id);

        int originalSize = userService.getAll().size();
        userService.delete(id);
        assertNotEquals(originalSize, userService.getAll().size());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteUserUnauthenticated() {
        deleteUser();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteUserWithoutPermission() {
        deleteUser();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void deleteUserAsOwner() {
        deleteUser();
    }

    @WithMockCustomUser(authorities = {"USER_READ_ALL", "USER_DELETE_ALL"})
    @Test
    public void deleteUserWithPermission() {
        deleteUser();
    }

    private void changeUserName() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        UUID id = UUID.fromString("10000000-0000-0000-0000-000000000000");
        addUser("user", "user", role, "user@mail.com", id);

        assertEquals("user", userService.get(id).getName());
        userService.changeName(id, "newName");
        assertEquals("newName", userService.get(id).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeUserNameUnauthenticated() {
        changeUserName();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeUserNameWithoutPermission() {
        changeUserName();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void changeUserNameAsOwner() {
        changeUserName();
    }

    @WithMockCustomUser(authorities = {"USER_READ_ALL", "USER_EDIT_ALL"})
    @Test
    public void changeUserNameWithPermission() {
        changeUserName();
    }

    private void changeUserPassword() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        UUID id = UUID.fromString("10000000-0000-0000-0000-000000000000");
        addUser("user", "user", role, "user@mail.com", id);

        assertEquals("user", userService.get(id).getPassword());
        userService.changePassword(id, "newPassword");
        assertEquals("newPassword", userService.get(id).getPassword());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeUserPasswordUnauthenticated() {
        changeUserPassword();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeUserPasswordWithoutPermission() {
        changeUserPassword();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void changeUserPasswordAsOwner() {
        changeUserPassword();
    }

    @WithMockCustomUser(authorities = {"USER_READ_ALL", "USER_EDIT_ALL"})
    @Test
    public void changeUserPasswordWithPermission() {
        changeUserPassword();
    }

    private void changeUserEmail() {
        Role role = addRole("user", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        UUID id = UUID.fromString("10000000-0000-0000-0000-000000000000");
        addUser("user", "user", role, "user@mail.com", id);

        assertEquals("user@mail.com", userService.get(id).getEmail());
        userService.changeEmail(id, "newEmail");
        assertEquals("newEmail", userService.get(id).getEmail());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeUserEmailUnauthenticated() {
        changeUserEmail();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeUserEmailWithoutPermission() {
        changeUserEmail();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void changeUserEmailAsOwner() {
        changeUserEmail();
    }

    @WithMockCustomUser(authorities = {"USER_READ_ALL", "USER_EDIT_ALL"})
    @Test
    public void changeUserEmailWithPermission() {
        changeUserEmail();
    }

    private Role addRole(String name, UUID id) {
        Role role = new Role(name);
        role.setId(id);
        roleDAO.add(role);
        return role;
    }

    private User addUser(String name,
                         String password,
                         Role role,
                         String email,
                         UUID id) {
        User user = new User(name, password, role, email);
        user.setId(id);
        userDAO.add(user);
        return user;
    }
}
