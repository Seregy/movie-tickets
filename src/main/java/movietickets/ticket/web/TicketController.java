package movietickets.ticket.web;

import movietickets.ticket.service.TicketService;
import movietickets.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * Buys ticket for current user.
     *
     * @param ids tickets' identifiers
     * @return http status
     */
    @PostMapping("/buy")
    public ResponseEntity buyTickets(@RequestParam("ids[]")
                                         final List<UUID> ids) {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        for (UUID seatId : ids) {
            ticketService.buy(seatId, userDetails.getId());
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Reserves ticket for current user.
     *
     * @param ids tickets' identifiers
     * @return http status
     */
    @PostMapping("/reserve")
    public ResponseEntity reserveTickets(@RequestParam("ids[]")
                                             final List<UUID> ids) {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        for (UUID seatId : ids) {
            ticketService.reserve(seatId, userDetails.getId());
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns ticket, canceling ticket purchase/reservation.
     *
     * @param id ticket's identifiers
     * @return http status
     */
    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestParam("ticket_id")
                                           final UUID id) {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        ticketService.cancel(id, userDetails.getId());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
