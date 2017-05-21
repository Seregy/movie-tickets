package movietickets.ticket.service;

import movietickets.ticket.Ticket;

import java.util.List;
import java.util.UUID;

/**
 * Ticket's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface TicketService {
    /**
     * Gets ticket with specified id.
     *
     * @param id ticket's id
     * @return ticket with specified id
     * or {@code null} if it wasn't found
     */
    Ticket get(UUID id);

    /**
     * Gets all existing tickets as list.
     *
     * @return list of tickets
     */
    List<Ticket> getAll();

    /**
     * Deletes ticket.
     *
     * @param ticket ticket to delete
     */
    void delete(Ticket ticket);

    /**
     * Deletes ticket.
     *
     * @param id id of the ticket to delete
     */
    void delete(UUID id);

    /**
     * Buys specified seat for specified user
     * and returns new ticket. Only available seats
     * can be bought.
     *
     * @param seatId seat's id
     * @param userId user's id
     * @return new ticket or {@code null}
     * if ticket couldn't be bought
     */
    Ticket buy(UUID seatId, UUID userId);

    /**
     * Reserves specified seat for specified user
     * and returns new ticket. Only available seats
     * can be reserved.
     *
     * @param seatId seat's id
     * @param userId user's id
     * @return new ticket or {@code null}
     * if ticket couldn't be reserved
     */
    Ticket reserve(UUID seatId, UUID userId);

    /**
     * Cancels buying/reserving a seat and returns
     * ticket.
     *
     * @param ticketId ticket's id
     */
    void cancel(UUID ticketId);
}
