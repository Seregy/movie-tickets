package movietickets.city.dao;

import movietickets.city.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Tests for City data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class CityDAOTest {
    @Autowired
    private CityDAO cityDAO;

    @Test
    public void findCity() {
        City[] cities = {
                new City("First"),
                new City("Second"),
                new City("Third")};
        for (City city : cities) {
            cityDAO.add(city);
        }

        assertEquals(cities[1], cityDAO.find(cities[1].getId()));
    }

    @Test
    public void findAllCities() {
        City[] cities = {
                new City("First"),
                new City("Second"),
                new City("Third"),
                new City("Fourth")};
        Arrays.sort(cities, Comparator.comparing(City::getName));
        for (City city : cities) {
            cityDAO.add(city);
        }

        assertArrayEquals(cities, cityDAO.findAll().toArray(new City[0]));
    }

    @Test
    public void addCity() {
        City city = new City("name");

        assertTrue(cityDAO.add(city));
        assertEquals(city, cityDAO.find(city.getId()));
    }

    @Test
    public void updateCity() {
        City city = new City("name");
        cityDAO.add(city);
        city.setName("another name");

        assertTrue(cityDAO.update(city));
        assertEquals(city.getName(),
                cityDAO.find(city.getId()).getName());
    }

    @Test
    public void deleteCity() {
        City city = new City("name");
        cityDAO.add(city);

        assertTrue(cityDAO.delete(city.getId()));
        assertNull(cityDAO.find(city.getId()));
    }
}
