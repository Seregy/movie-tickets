package movietickets.ticket.web;

import movietickets.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Ticket controller, responsible for giving pages
 * and resources related to ticket.
 *
 * @author Seregy
 */
@Controller
public class TicketController {
    private static Logger log =
            Logger.getLogger(TicketController.class.getName());

    private final TicketService ticketService;


    /**
     * Constructs new cinema controller with given Ticket Service.
     *
     * @param ticketService ticket service
     */
    @Autowired
    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Shows page with cinemas.
     *
     * @return name of jsp-page
     */
    @GetMapping("/ticket")
    public String showTicketsPages() {
        return "admin/ticket";
    }

    /**
     * Shows table, filled with tickets.
     *
     * @return name of jsp-page
     */
    @GetMapping("/tickets")
    public ModelAndView showTickets() {
        ModelAndView modelAndView = new ModelAndView("admin/tickets_table");
        modelAndView.addObject("tickets", ticketService.getAll());
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
        ticketService.buy(UUID.fromString(seatId), UUID.fromString(userId));
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
        ticketService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
