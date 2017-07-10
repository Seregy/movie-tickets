package movietickets.ticket.service;

import movietickets.core.WithMockCustomUser;
import movietickets.hall.layout.SeatType;
import movietickets.seat.Seat;
import movietickets.seat.SeatStatus;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.ticket.dao.TicketDAO;
import movietickets.user.User;
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
 * Tests for TicketServiceDAO.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TicketServiceDAOTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private SeatDAO seatDAO;
    @Autowired
    private TicketDAO ticketDAO;

    private void getTicket() {
        User user = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Seat seat = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Ticket ticket = addTicket(seat, user, UUID.fromString("10000000-0000-0000-0000-000000000001"));

        assertNotNull(ticketService.get(ticket.getId()));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getTicketUnauthenticated() {
        getTicket();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void getTicketWithoutPermission() {
        getTicket();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void getTicketAsOwner() {
        getTicket();
    }

    @WithMockCustomUser(authorities = "TICKET_READ_ALL")
    @Test
    public void getTicketWithPermission() {
        getTicket();
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getAllTicketsUnauthenticated() {
        User user1 = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        User user2 = addUser(UUID.fromString("20000000-0000-0000-0000-000000000000"));
        Seat seat1 = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Seat seat2 = addSeat(2, 2, UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addTicket(seat1, user1, UUID.fromString("10000000-0000-0000-0000-000000000001"));
        addTicket(seat2, user2, UUID.fromString("20000000-0000-0000-0000-000000000002"));

        ticketService.getAll().isEmpty();
    }

    @WithMockCustomUser
    @Test
    public void getAllTicketsWithoutPermission() {
        User user1 = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        User user2 = addUser(UUID.fromString("20000000-0000-0000-0000-000000000000"));
        Seat seat1 = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Seat seat2 = addSeat(2, 2, UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addTicket(seat1, user1, UUID.fromString("10000000-0000-0000-0000-000000000001"));
        addTicket(seat2, user2, UUID.fromString("20000000-0000-0000-0000-000000000002"));

        assertTrue(ticketService.getAll().isEmpty());
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void getAllTicketsAsOneOfOwners() {
        User user1 = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        User user2 = addUser(UUID.fromString("20000000-0000-0000-0000-000000000000"));
        Seat seat1 = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Seat seat2 = addSeat(2, 2, UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addTicket(seat1, user1, UUID.fromString("10000000-0000-0000-0000-000000000001"));
        addTicket(seat2, user2, UUID.fromString("20000000-0000-0000-0000-000000000002"));

        List<Ticket> tickets = ticketService.getAll();
        assertEquals(1, tickets.size());
        assertEquals(user1, tickets.get(0).getUser());
    }

    @WithMockCustomUser(authorities = "TICKET_READ_ALL")
    @Test
    public void getAllTicketsWithPermission() {
        User user1 = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        User user2 = addUser(UUID.fromString("20000000-0000-0000-0000-000000000000"));
        Seat seat1 = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Seat seat2 = addSeat(2, 2, UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addTicket(seat1, user1, UUID.fromString("10000000-0000-0000-0000-000000000001"));
        addTicket(seat2, user2, UUID.fromString("20000000-0000-0000-0000-000000000002"));

        List<Ticket> tickets = ticketService.getAll();
        assertEquals(2, tickets.size());
    }

    private List<Ticket> getAllTicketsByUserId(UUID userId) {
        User user1 = addUser(userId);
        User user2 = addUser(UUID.fromString("20000000-0000-0000-0000-000000000000"));
        Seat seat1 = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Seat seat2 = addSeat(2, 2, UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addTicket(seat1, user1, UUID.fromString("10000000-0000-0000-0000-000000000001"));
        addTicket(seat2, user2, UUID.fromString("20000000-0000-0000-0000-000000000002"));
        return ticketService.getAll(user1.getId());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getAllTicketsByUserIdUnauthenticated() {
        List<Ticket> tickets = getAllTicketsByUserId(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        assertTrue(tickets.isEmpty());
    }

    @WithMockCustomUser
    @Test
    public void getAllTicketsByUserIdWithoutPermission() {
        List<Ticket> tickets = getAllTicketsByUserId(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        assertTrue(tickets.isEmpty());
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void getAllTicketsByUserIdAsOwner() {
        List<Ticket> tickets = getAllTicketsByUserId(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        assertEquals(1, tickets.size());
        assertEquals(UUID.fromString("10000000-0000-0000-0000-000000000000"), tickets.get(0).getUser().getId());
    }

    @WithMockCustomUser(authorities = "TICKET_READ_ALL")
    @Test
    public void getAllTicketsByUserIdWithPermission() {
        List<Ticket> tickets = getAllTicketsByUserId(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        assertEquals(1, tickets.size());
        assertEquals(UUID.fromString("10000000-0000-0000-0000-000000000000"), tickets.get(0).getUser().getId());
    }

    @WithMockCustomUser
    @Test
    public void buyTicket() {
        assertEquals(ticketDAO.findAll().size(), 0);

        User user = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Seat seat = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        ticketService.buy(seat.getId(), user.getId());

        assertEquals(1, ticketDAO.findAll().size());
        assertEquals(SeatStatus.PURCHASED, ticketDAO.findAll().get(0).getSeat().getSeatStatus());
        assertEquals(user, ticketDAO.findAll().get(0).getUser());
    }

    @WithMockCustomUser
    @Test
    public void reserveTicket() {
        assertEquals(ticketDAO.findAll().size(), 0);

        User user = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Seat seat = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        ticketService.reserve(seat.getId(), user.getId());

        assertEquals(1, ticketDAO.findAll().size());
        assertEquals(SeatStatus.RESERVED, ticketDAO.findAll().get(0).getSeat().getSeatStatus());
        assertEquals(user, ticketDAO.findAll().get(0).getUser());
    }

    private void cancelTicket() {
        User user = addUser(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Seat seat = addSeat(1, 1, UUID.fromString("00000000-0000-0000-0000-000000000001"));
        UUID id = UUID.fromString("10000000-0000-0000-0000-000000000001");
        addTicket(seat, user, id);

        int originalSize = ticketDAO.findAll().size();
        ticketService.cancel(id);
        assertNotEquals(originalSize, ticketService.getAll().size());
        assertTrue(ticketDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void cancelTicketUnauthenticated() {
        cancelTicket();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void cancelTicketWithoutPermission() {
        cancelTicket();
    }

    @WithMockCustomUser(id = "10000000-0000-0000-0000-000000000000")
    @Test
    public void cancelTicketAsOwner() {
        cancelTicket();
    }

    @WithMockCustomUser(authorities = {"TICKET_READ_ALL", "TICKET_DELETE_ALL"})
    @Test
    public void cancelTicketWithPermission() {
        cancelTicket();
    }

    private Ticket addTicket(Seat seat, User user, UUID id) {
        Ticket ticket = new Ticket(seat, user);
        ticket.setId(id);
        seat.setTicket(ticket);
        user.addTicket(ticket);
        ticketDAO.add(ticket);
        userDAO.update(user);
        seatDAO.update(seat);
        return ticket;
    }

    private Seat addSeat(int rowNumber, int seatNumber, UUID id) {
        Seat seat = new Seat(rowNumber, seatNumber, SeatType.REGULAR, 10);
        seat.setId(id);
        seatDAO.add(seat);
        return seat;
    }

    private User addUser(UUID id) {
        Role role = new Role("user");
        role.setId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        roleDAO.add(role);

        User user = new User("user_" + id.toString(),
                "user",
                role,
                "user@mail.com");
        user.setId(id);
        userDAO.add(user);
        return user;
    }
}
