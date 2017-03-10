package ticket;

import core.DAO;
import user.User;
import user.UserDAODefault;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for TicketDAODefault and TicketDAOMongo.
 */
public class TicketDAOTest {
    private final User[] users = {
            new User(UUID.randomUUID(), "Full name", "Nickname",
                    "pass", "salt", "email"),
            new User(UUID.randomUUID(), "Full name 2", "Nickname 2",
                    "pass", "salt", "email"),
            new User(UUID.randomUUID(), "Full name 3", "Nickname 3",
                    "pass", "salt", "email"),
            new User(UUID.randomUUID(), "Full name 4", "Nickname 4",
                    "pass", "salt", "email")};
    private final Ticket[] tickets = {
            new Ticket(UUID.randomUUID(), users[0], 1, 3),
            new Ticket(UUID.randomUUID(), users[1], 2, 5),
            new Ticket(UUID.randomUUID(), users[2], 3, 7),
            new Ticket(UUID.randomUUID(), users[3], 4, 9)};

    private DAO<Ticket, UUID> daoDefault = new TicketDAODefault(new UserDAODefault());
    private DAO<Ticket, UUID> daoMongo = new TicketDAOMongo();

    @org.junit.Test
    public void testDefault() throws Exception {
        add(daoDefault);
        find(daoDefault);
        update(daoDefault);
        findAll(daoDefault);
        delete(daoDefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(daoMongo);
        //find(daoMongo);
        findAll(daoMongo);
        update(daoMongo);
        delete(daoMongo);
    }

    private void find(DAO<Ticket, UUID> ticketDAO) {
        assertEquals(tickets[2], ticketDAO.find(tickets[2].getId()));
    }

    private void findAll(DAO<Ticket, UUID> ticketDAO) {
        assertEquals(tickets.length, ticketDAO.findAll().size());
    }

    private void add(DAO<Ticket, UUID> ticketDAO) {
        for (Ticket ticket : tickets) {
            ticketDAO.add(ticket);
        }
    }

    private void update(DAO<Ticket, UUID> ticketDAO) {
        ticketDAO.update(new Ticket(tickets[2].getId(), users[0], 12, 12));
        assertNotEquals(tickets[2].getRow(), ticketDAO.find(tickets[2].getId()).getRow());
        assertNotEquals(tickets[2].getUser().getFullName(),
                ticketDAO.find(users[2].getId()).getUser().getFullName());
    }

    private void delete(DAO<Ticket, UUID> ticketDAO) {
        for (Ticket ticket : tickets) {
            ticketDAO.delete(ticket.getId());
        }
    }
}
