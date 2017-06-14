package movietickets.ticket.service;

import movietickets.seat.Seat;
import movietickets.seat.SeatStatus;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.ticket.dao.TicketDAO;
import movietickets.user.User;
import movietickets.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Session's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class TicketServiceDAO implements TicketService {
    private final TicketDAO ticketDAO;
    private final SeatDAO seatDao;
    private final UserDAO userDAO;

    /**
     * Constructs new ticket service with given Ticket DAO, Seat DAO
     * and User DAO.
     *
     * @param ticketDAO ticket data access object
     * @param seatDao seat data access object
     * @param userDAO user data access object
     */
    @Autowired
    public TicketServiceDAO(final TicketDAO ticketDAO,
                            final SeatDAO seatDao,
                            final UserDAO userDAO) {
        this.ticketDAO = ticketDAO;
        this.seatDao = seatDao;
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Ticket get(final UUID id) {
        return ticketDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Ticket> getAll() {
        return ticketDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void delete(final Ticket ticket) {
        delete(ticket.getId());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final UUID id) {
        ticketDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Ticket buy(final UUID seatId, final UUID userId) {
        Seat seat = seatDao.find(seatId);
        User user = userDAO.find(userId);

        if (seat.getSeatStatus() == SeatStatus.AVAILABLE) {
            Ticket ticket = new Ticket(seat, user);
            seat.setTicket(ticket);
            seat.setSeatStatus(SeatStatus.PURCHASED);

            user.addTicket(ticket);
            ticketDAO.add(ticket);
            userDAO.update(user);
            seatDao.update(seat);
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
        Seat seat = seatDao.find(seatId);
        User user = userDAO.find(userId);

        if (seat.getSeatStatus() == SeatStatus.AVAILABLE) {
            Ticket ticket = new Ticket(seat, user);
            seat.setTicket(ticket);
            seat.setSeatStatus(SeatStatus.RESERVED);

            user.addTicket(ticket);
            ticketDAO.add(ticket);
            userDAO.update(user);
            seatDao.update(seat);
            return ticket;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void cancel(final UUID ticketId,
                       final UUID userId) {
        Ticket ticket = ticketDAO.find(ticketId);
        User user = userDAO.find(userId);
        if (ticket.getUser().equals(user)) {
            ticket.getSeat().setSeatStatus(SeatStatus.AVAILABLE);
            ticketDAO.delete(ticket.getId());
            ticket.getSeat().setTicket(null);
            seatDao.update(ticket.getSeat());
            user.removeTicket(ticket);
            userDAO.update(user);
        }
    }
}
