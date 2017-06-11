package movietickets.cinema.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Cinema's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class CinemaServiceDAO implements CinemaService {
    private final CinemaDAO cinemaDAO;

    /**
     * Constructs new cinema service with given Cinema DAO.
     *
     * @param cinemaDAO cinema data access object
     */
    @Autowired
    public CinemaServiceDAO(final CinemaDAO cinemaDAO) {
        this.cinemaDAO = cinemaDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_ADD')")
    @Transactional
    @Override
    public void add(final Cinema cinema) {
        cinemaDAO.add(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cinema get(final UUID id) {
        return cinemaDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Cinema> getAll() {
        return cinemaDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Cinema> getAll(final City city) {
        return cinemaDAO.findAll().stream()
                .filter(cinema ->
                        cinema.getCity().equals(city))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final Cinema cinema) {
        delete(cinema.getId());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final UUID id) {
        cinemaDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeName(final UUID cinemaId, final String newName) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        cinema.setName(newName);
        cinemaDAO.update(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeLocation(final UUID cinemaId, final String newLocation) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        cinema.setAddress(newLocation);
        cinemaDAO.update(cinema);
    }
}
