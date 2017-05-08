package movietickets.ticket.web;

import movietickets.seat.Seat;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.ticket.dao.TicketDAO;
import movietickets.user.User;
import movietickets.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Ticket controller, responsible for giving pages
 * and resources related to ticket.
 *
 * @author Seregy
 */
@Controller
@Transactional
public class TicketController {
    private static Logger log =
            Logger.getLogger(TicketController.class.getName());

    private final TicketDAO ticketDAO;
    private final SeatDAO seatDAO;
    private final UserDAO userDAO;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs new cinema controller with given Ticket DAO,
     * Seat DAO and User DAO.
     *
     * @param ticketDAO ticket data access object
     * @param seatDAO seat data access object
     * @param userDAO user data access object
     */
    @Autowired
    public TicketController(final TicketDAO ticketDAO,
                            final SeatDAO seatDAO,
                            final UserDAO userDAO) {
        this.ticketDAO = ticketDAO;
        this.seatDAO = seatDAO;
        this.userDAO = userDAO;
    }

    /**
     * Shows page with cinemas.
     *
     * @return name of jsp-page
     */
    @GetMapping("/ticket")
    public String showTicketsPages() {
        return "ticket";
    }

    /**
     * Shows table, filled with tickets.
     *
     * @return name of jsp-page
     */
    @GetMapping("/tickets")
    public ModelAndView showTickets() {
        ModelAndView modelAndView = new ModelAndView("tickets_table");
        modelAndView.addObject("tickets", ticketDAO.findAll());
        return modelAndView;
    }

    /**
     * Adds new ticket with given seat id and user id.
     *
     * @param seatId seat identifier
     * @param userId user identifier
     * @return response code
     */
    @PostMapping("/tickets")
    public ResponseEntity addTicket(@RequestParam("seat_id")
                                        final String seatId,
                                    @RequestParam("user_id")
                                        final String userId) {
        Seat seat = seatDAO.find(UUID.fromString(seatId));
        User user = userDAO.find(UUID.fromString(userId));
        Ticket ticket = new Ticket(seat, user);
        ticketDAO.add(ticket);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes ticket with given id.
     *
     * @param id identifier of the ticket
     * @return response code
     */
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity deleteTicket(@PathVariable("id") final String id) {
        ticketDAO.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
