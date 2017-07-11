package movietickets.cinema.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.city.City;
import movietickets.city.dao.CityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
    private final CityDAO cityDAO;

    /**
     * Constructs new cinema service.
     *
     * @param cinemaDAO cinema data access object
     * @param cityDAO city data access object
     */
    @Autowired
    public CinemaServiceDAO(final CinemaDAO cinemaDAO,
                            final CityDAO cityDAO) {
        this.cinemaDAO = cinemaDAO;
        this.cityDAO = cityDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#cinema, 'add')")
    @Transactional
    @Override
    public void add(@P("cinema") final Cinema cinema, final UUID cityId) {
        cinemaDAO.add(cinema);
        City city = cityDAO.find(cityId);
        city.addCinema(cinema);
        cityDAO.update(city);
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
    @PreAuthorize("hasPermission(#id, 'Cinema', 'delete')")
    @Transactional
    @Override
    public void delete(@P("id") final UUID id) {
        cinemaDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#cinemaId, 'Cinema', 'edit')")
    @Transactional
    @Override
    public void changeName(@P("cinemaId") final UUID cinemaId,
                           final String newName) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        cinema.setName(newName);
        cinemaDAO.update(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#cinemaId, 'Cinema', 'edit')")
    @Transactional
    @Override
    public void changeCity(@P("cinemaId") final UUID cinemaId,
                           final UUID newCityId) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        City oldCity = cinema.getCity();
        City newCity = cityDAO.find(newCityId);
        oldCity.removeCinema(cinema);
        newCity.addCinema(cinema);
        cityDAO.update(oldCity);
        cityDAO.update(newCity);
        cinemaDAO.update(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#cinemaId, 'Cinema', 'edit')")
    @Transactional
    @Override
    public void changeAddress(@P("cinemaId") final UUID cinemaId,
                              final String newAddress) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        cinema.setAddress(newAddress);
        cinemaDAO.update(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#cinemaId, 'Cinema', 'edit')")
    @Transactional
    @Override
    public void changePhone(@P("cinemaId") final UUID cinemaId,
                            final String newPhone) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        cinema.setPhone(newPhone);
        cinemaDAO.update(cinema);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#cinemaId, 'Cinema', 'edit')")
    @Transactional
    @Override
    public void changeWebsite(@P("cinemaId") final UUID cinemaId,
                              final String newWebsite) {
        Cinema cinema = cinemaDAO.find(cinemaId);
        cinema.setWebsite(newWebsite);
        cinemaDAO.update(cinema);
    }
}
