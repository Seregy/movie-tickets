package dao;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import movietickets.hall.layout.SeatStatus;
import movietickets.hall.layout.dao.LayoutDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import movietickets.seat.Seat;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.ticket.dao.TicketDAO;
import movietickets.user.User;
import movietickets.user.dao.UserDAO;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for models' DAO.
 *
 * @author Seregy
 */
@WebAppConfiguration
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = core.TestConfiguration.class)
@Transactional
public class DAOTest {
    @Autowired
    private CinemaDAO cinemaDAO;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HallDAO hallDAO;
    @Autowired
    private LayoutDAO layoutDAO;
    @Autowired
    private SeatDAO seatDAO;

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
        Seat seat1 = new Seat(10, 10);
        Seat seat2 = new Seat(20, 20);
        Seat seat3 = new Seat(30, 30);
        List<Seat> seats = Arrays.asList(seat1, seat2, seat3);
        for (Seat seat : seats) {
            assertTrue(seatDAO.add(seat));
        }

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
        }

        Ticket ticket1 = new Ticket(seat1, user1);
        Ticket ticket2 = new Ticket(seat2, user2);
        Ticket ticket3 = new Ticket(seat3, user3);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2, ticket3);

        for (Ticket ticket : tickets) {
            assertTrue(ticketDAO.add(ticket));
            assertEquals(ticket, ticketDAO.find(ticket.getId()));
        }
        assertEquals(tickets.size(), ticketDAO.findAll().size());

        ticket1.setUser(user3);
        Seat seat4 = new Seat(40, 40);
        ticket2.setSeat(seat4);

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

    @Test
    public void testHall() throws Exception {
        Hall hall1 = new Hall("Test name 1");
        Hall hall2 = new Hall("Test name 2");
        Hall hall3 = new Hall("Test name 3");

        List<Hall> halls = Arrays.asList(hall1, hall2, hall3);

        for (Hall hall : halls) {
            assertTrue(hallDAO.add(hall));
            assertEquals(hall, hallDAO.find(hall.getId()));
        }
        assertEquals(halls.size(), hallDAO.findAll().size());

        hall1.setName(hall1.getName() + " updated");

        for (Hall hall : halls) {
            assertTrue(hallDAO.update(hall));
            assertEquals(hall, hallDAO.find(hall.getId()));
        }

        for (Hall hall : halls) {
            assertTrue(hallDAO.delete(hall.getId()));
        }
        assertTrue(hallDAO.findAll().isEmpty());
    }

    @Test
    public void testLayout() throws Exception {
        Layout layout1 = new Layout(3, 3);
        Layout layout2 = new Layout(4, 4);
        Layout layout3 = new Layout(5, 5);

        List<Layout> layouts = Arrays.asList(layout1, layout2, layout3);

        for (Layout layout : layouts) {
            assertTrue(layoutDAO.add(layout));
            assertEquals(layout, layoutDAO.find(layout.getId()));
        }
        assertEquals(layouts.size(), layoutDAO.findAll().size());

        layout1.setRowsAmount(13);
        layout2.setSeatsAmount(14);
        SeatStatus[][] seatStatuses = layout3.getSeatsStatuses();
        seatStatuses[1][1] = SeatStatus.EMPTY;
        layout3.setSeats(seatStatuses);

        for (Layout layout : layouts) {
            assertTrue(layoutDAO.update(layout));
            assertEquals(layout, layoutDAO.find(layout.getId()));
        }

        for (Layout layout : layouts) {
            assertTrue(layoutDAO.delete(layout.getId()));
        }
        assertTrue(layoutDAO.findAll().isEmpty());
    }

    @Test
    public void testSeat() throws Exception {
        Seat seat1 = new Seat(10, 10);
        Seat seat2 = new Seat(20, 20);
        Seat seat3 = new Seat(30, 30);

        List<Seat> seats = Arrays.asList(seat1, seat2, seat3);

        for (Seat seat : seats) {
            assertTrue(seatDAO.add(seat));
            assertEquals(seat, seatDAO.find(seat.getId()));
        }
        assertEquals(seats.size(), seatDAO.findAll().size());

        seat1.setRowNumber(13);
        seat2.setSeatNumber(24);

        for (Seat seat : seats) {
            assertTrue(seatDAO.update(seat));
            assertEquals(seat, seatDAO.find(seat.getId()));
        }

        for (Seat seat : seats) {
            assertTrue(seatDAO.delete(seat.getId()));
        }
        assertTrue(seatDAO.findAll().isEmpty());
    }

}
