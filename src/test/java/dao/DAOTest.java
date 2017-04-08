package dao;

import cinema.Cinema;
import cinema.dao.CinemaDAO;
import core.web.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ticket.Ticket;
import ticket.dao.TicketDAO;
import user.User;
import user.dao.UserDAO;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for models' DAO.
 *
 * @author Seregy
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfiguration.class)
public class DAOTest {
    @Autowired
    private CinemaDAO cinemaDAO;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void testCinema() throws Exception {
        Cinema cinema1 = new Cinema("Test cinema 1", "Test location 1");
        Cinema cinema2 = new Cinema("Test cinema 2", "Test location 2");
        Cinema cinema3 = new Cinema("Test cinema 3", "Test location 3");
        List<Cinema> cinemas = Arrays.asList(cinema1, cinema2, cinema3);

        for (Cinema cinema : cinemas) {
            assertTrue(cinemaDAO.add(cinema));
            assertEquals(cinema, cinemaDAO.find(cinema.getId()));
        }
        assertEquals(cinemas.size(), cinemaDAO.findAll().size());

        cinema1.setName(cinema1.getName() + " updated");
        cinema2.setLocation(cinema2.getLocation() + " updated");

        for (Cinema cinema : cinemas) {
            assertTrue(cinemaDAO.update(cinema));
            assertEquals(cinema, cinemaDAO.find(cinema.getId()));
        }

        for (Cinema cinema : cinemas) {
            assertTrue(cinemaDAO.delete(cinema.getId()));
        }
        assertTrue(cinemaDAO.findAll().isEmpty());
    }

    @Test
    public void testTicket() throws Exception {
        Ticket ticket1 = new Ticket(1, 1);
        Ticket ticket2 = new Ticket(2, 2);
        Ticket ticket3 = new Ticket(3, 3);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2, ticket3);

        for (Ticket ticket : tickets) {
            assertTrue(ticketDAO.add(ticket));
            assertEquals(ticket, ticketDAO.find(ticket.getId()));
        }
        assertEquals(tickets.size(), ticketDAO.findAll().size());

        ticket1.setRow(10);
        ticket2.setSeat(20);

        for (Ticket ticket : tickets) {
            assertTrue(ticketDAO.update(ticket));
            assertEquals(ticket, ticketDAO.find(ticket.getId()));
        }

        for (Ticket ticket : tickets) {
            assertTrue(ticketDAO.delete(ticket.getId()));
        }
        assertTrue(ticketDAO.findAll().isEmpty());
    }

    @Test
    public void testUser() throws Exception {
        User user1 = new User("Test name 1",
                "Test username 1",
                "Test password 1",
                "Test salt 1",
                "Test email 1");
        User user2 = new User("Test name 2",
                "Test username 2",
                "Test password 2",
                "Test salt 2",
                "Test email 2");
        User user3 = new User("Test name 3",
                "Test username 3",
                "Test password 3",
                "Test salt 3",
                "Test email 3");
        List<User> users = Arrays.asList(user1, user2, user3);

        for (User user : users) {
            assertTrue(userDAO.add(user));
            assertEquals(user, userDAO.find(user.getId()));
        }
        assertEquals(users.size(), userDAO.findAll().size());

        user1.setFullName(user1.getFullName() + " updated");
        user1.setUserName(user1.getUserName() + " updated");
        user2.setPassword(user2.getPassword() + " updated");
        user2.setSalt(user2.getSalt() + " updated");
        user3.setEmail(user3.getEmail() + " updated");

        for (User user : users) {
            assertTrue(userDAO.update(user));
            assertEquals(user, userDAO.find(user.getId()));
        }

        for (User user : users) {
            assertTrue(userDAO.delete(user.getId()));
        }
        assertTrue(userDAO.findAll().isEmpty());
    }
}
