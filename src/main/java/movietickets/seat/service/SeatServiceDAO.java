package movietickets.seat.service;

import movietickets.seat.Seat;
import movietickets.seat.dao.SeatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Seat's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class SeatServiceDAO implements SeatService {
    private final SeatDAO seatDAO;

    /**
     * Constructs new seat service with given Seat DAO.
     *
     * @param seatDAO seat data access object
     */
    @Autowired
    public SeatServiceDAO(final SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Transactional
    @Override
    public void add(final Seat seat) {
        seatDAO.add(seat);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Seat get(final UUID id) {
        return seatDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Seat> getAll() {
        return seatDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(final Seat seat) {
        delete(seat.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(final UUID id) {
        seatDAO.delete(id);
    }
}
