package movietickets.ticket.dao;

import movietickets.hall.layout.SeatType;
import movietickets.seat.Seat;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.user.User;
import movietickets.user.dao.UserDAO;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Tests for Ticket data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class TicketDAOTest {
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private SeatDAO seatDAO;

    @Test
    public void findTicket() {
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

        Seat[] seats = {
                new Seat(1, 1, SeatType.REGULAR, 1),
                new Seat(2, 2, SeatType.REGULAR, 2),
                new Seat(3, 3, SeatType.REGULAR, 3)};
        for (Seat seat : seats) {
            seatDAO.add(seat);
        }

        Ticket[] tickets = {
                new Ticket(seats[0], users[0]),
                new Ticket(seats[1], users[1]),
                new Ticket(seats[2], users[2])};
        for (Ticket ticket : tickets) {
            ticketDAO.add(ticket);
        }

        assertEquals(tickets[1], ticketDAO.find(tickets[1].getId()));
    }

    @Test
    public void findAllTickets() {
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

        Seat[] seats = {
                new Seat(1, 1, SeatType.REGULAR, 1),
                new Seat(2, 2, SeatType.REGULAR, 2),
                new Seat(3, 3, SeatType.REGULAR, 3),
                new Seat(4, 4, SeatType.REGULAR, 4)};
        for (Seat seat : seats) {
            seatDAO.add(seat);
        }

        Ticket[] tickets = {
                new Ticket(seats[0], users[0]),
                new Ticket(seats[1], users[1]),
                new Ticket(seats[2], users[2]),
                new Ticket(seats[3], users[3])};
        for (Ticket ticket : tickets) {
            ticketDAO.add(ticket);
        }

        assertArrayEquals(tickets, ticketDAO.findAll().toArray(new Ticket[0]));
    }

    @Test
    public void addTicket() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        User user = new User("Name", "Password", role, "Email");
        userDAO.add(user);

        Seat seat = new Seat(7, 7, SeatType.REGULAR, 10);
        seatDAO.add(seat);

        Ticket ticket = new Ticket(seat, user);
        assertTrue(ticketDAO.add(ticket));
        assertEquals(ticket, ticketDAO.find(ticket.getId()));
    }

    @Test
    public void updateTicket() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        User user1 = new User("Name", "Password", role, "Email");
        User user2 = new User("Another name", "Password", role, "Email");
        userDAO.add(user1);
        userDAO.add(user2);

        Seat seat = new Seat(7, 7, SeatType.REGULAR, 10);
        seatDAO.add(seat);

        Ticket ticket = new Ticket(seat, user1);
        ticketDAO.add(ticket);
        ticket.setUser(user2);

        assertTrue(ticketDAO.update(ticket));
        assertEquals(ticket.getUser(),
                ticketDAO.find(ticket.getId()).getUser());
    }

    @Test
    public void deleteTicket() {
        Role role = new Role("Role name");
        roleDAO.add(role);

        User user = new User("Name", "Password", role, "Email");
        userDAO.add(user);

        Seat seat = new Seat(7, 7, SeatType.REGULAR, 10);
        seatDAO.add(seat);

        Ticket ticket = new Ticket(seat, user);
        ticketDAO.add(ticket);

        assertTrue(ticketDAO.delete(ticket.getId()));
        assertNull(ticketDAO.find(ticket.getId()));
    }
}
