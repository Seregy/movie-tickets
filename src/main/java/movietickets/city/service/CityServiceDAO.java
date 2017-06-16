package movietickets.city.service;

import movietickets.city.City;
import movietickets.city.dao.CityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * City's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class CityServiceDAO implements CityService {
    private final CityDAO cityDAO;

    /**
     * Constructs new cinema service.
     *
     * @param cityDAO city data access object
     */
    @Autowired
    public CityServiceDAO(final CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void add(final City city) {
        cityDAO.add(city);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public City get(final UUID id) {
        return cityDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<City> getAll() {
        return cityDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(final City city) {
        delete(city.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(final UUID id) {
        cityDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changeName(final UUID cityId,
                           final String newName) {
        City city = cityDAO.find(cityId);
        city.setName(newName);
        cityDAO.update(city);
    }
}
