package movietickets.hall.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Hall's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class HallServiceDAO implements HallService {
    private final HallDAO hallDAO;
    private final CinemaDAO cinemaDAO;

    /**
     * Constructs new hall service with given Hall DAO,
     * Layout DAO and Cinema DAO.
     *
     * @param hallDAO cinema data access object
     * @param cinemaDAO cinema data access object
     */
    @Autowired
    public HallServiceDAO(final HallDAO hallDAO,
                          final CinemaDAO cinemaDAO) {
        this.hallDAO = hallDAO;
        this.cinemaDAO = cinemaDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_ADD')")
    @Transactional
    @Override
    public void add(final Hall hall, final Layout layout, final UUID cinemaId) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        hall.setLayout(layout);
        cinema.addHall(hall);
        hallDAO.add(hall);
        cinemaDAO.update(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Hall get(final UUID id) {
        return hallDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Hall> getAll() {
        return hallDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Hall> getAll(final UUID cinemaId) {
        return hallDAO.findAll().stream()
                .filter(hall -> hall.getCinema()
                        .getId().equals(cinemaId))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final Hall hall) {
        delete(hall.getId());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final UUID id) {
        hallDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeName(final UUID hallId, final String newName) {
        Hall hall = hallDAO.find(hallId);
        hall.setName(newName);
        hallDAO.update(hall);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeLayout(final UUID hallId, final Layout newLayout) {
        Hall hall = hallDAO.find(hallId);
        hall.setLayout(newLayout);
        hallDAO.update(hall);
    }
}
