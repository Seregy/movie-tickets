package movietickets.ticket.service;

import movietickets.seat.Seat;
import movietickets.seat.SeatStatus;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.ticket.dao.TicketDAO;
import movietickets.user.User;
import movietickets.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Session's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class TicketServiceDAO implements TicketService {
    private final TicketDAO ticketDAO;
    private final SeatDAO seatDAO;
    private final UserDAO userDAO;

    /**
     * Constructs new ticket service with given Ticket DAO, Seat DAO
     * and User DAO.
     *
     * @param ticketDAO ticket data access object
     * @param seatDAO seat data access object
     * @param userDAO user data access object
     */
    @Autowired
    public TicketServiceDAO(final TicketDAO ticketDAO,
                            final SeatDAO seatDAO,
                            final UserDAO userDAO) {
        this.ticketDAO = ticketDAO;
        this.seatDAO = seatDAO;
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PostAuthorize("hasPermission(returnObject, 'read')")
    @Transactional
    @Override
    public Ticket get(final UUID id) {
        return ticketDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @PostFilter("hasPermission(filterObject, 'read')")
    @Transactional
    @Override
    public List<Ticket> getAll() {
        return ticketDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @PostFilter("hasPermission(filterObject, 'read')")
    @Transactional
    @Override
    public List<Ticket> getAll(final UUID userId) {
        return ticketDAO.findAll().stream()
                .filter(ticket -> ticket.getUser()
                        .getId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Ticket buy(final UUID seatId, final UUID userId) {
        Seat seat = seatDAO.find(seatId);
        User user = userDAO.find(userId);

        if (seat.getSeatStatus() == SeatStatus.AVAILABLE) {
            Ticket ticket = new Ticket(seat, user);
            seat.setTicket(ticket);
            seat.setSeatStatus(SeatStatus.PURCHASED);

            user.addTicket(ticket);
            ticketDAO.add(ticket);
            userDAO.update(user);
            seatDAO.update(seat);
            return ticket;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Ticket reserve(final UUID seatId, final UUID userId) {
        Seat seat = seatDAO.find(seatId);
        User user = userDAO.find(userId);

        if (seat.getSeatStatus() == SeatStatus.AVAILABLE) {
            Ticket ticket = new Ticket(seat, user);
            seat.setTicket(ticket);
            seat.setSeatStatus(SeatStatus.RESERVED);

            user.addTicket(ticket);
            ticketDAO.add(ticket);
            userDAO.update(user);
            seatDAO.update(seat);
            return ticket;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#ticketId, 'Ticket', 'delete')")
    @Transactional
    @Override
    public void cancel(@P("ticketId") final UUID ticketId) {
        Ticket ticket = ticketDAO.find(ticketId);
        User user = userDAO.find(ticket.getUser().getId());
        ticket.getSeat().setSeatStatus(SeatStatus.AVAILABLE);
        ticketDAO.delete(ticket.getId());
        ticket.getSeat().setTicket(null);
        seatDAO.update(ticket.getSeat());
        user.removeTicket(ticket);
        userDAO.update(user);
    }
}
