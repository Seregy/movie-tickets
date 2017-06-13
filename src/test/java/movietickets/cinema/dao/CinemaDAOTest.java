package movietickets.cinema.dao;

import movietickets.cinema.Cinema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Tests for Cinema data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.TestConfiguration.class)
@Transactional
public class CinemaDAOTest {
    @Autowired
    private CinemaDAO cinemaDAO;

    @Test
    public void findCinema() {
        Cinema[] cinemas = {
                new Cinema("First", "first"),
                new Cinema("Second", "second"),
                new Cinema("Third", "third")};
        for (Cinema cinema : cinemas) {
            cinemaDAO.add(cinema);
        }

        assertEquals(cinemas[1], cinemaDAO.find(cinemas[1].getId()));
    }

    @Test
    public void findAllCinemas() {
        Cinema[] cinemas = {new Cinema("First", "first"),
                new Cinema("Second", "second"),
                new Cinema("Third", "third"),
                new Cinema("Fourth", "fourth")};
        Arrays.sort(cinemas, Comparator.comparing(Cinema::getName));
        for (Cinema cinema : cinemas) {
            cinemaDAO.add(cinema);
        }

        assertArrayEquals(cinemas, cinemaDAO.findAll().toArray(new Cinema[0]));
    }

    @Test
    public void addCinema() {
        Cinema cinema = new Cinema("name", "location");

        assertTrue(cinemaDAO.add(cinema));
        assertEquals(cinema, cinemaDAO.find(cinema.getId()));
    }

    @Test
    public void updateCinema() {
        Cinema cinema = new Cinema("name", "location");
        cinemaDAO.add(cinema);
        cinema.setName("another name");

        assertTrue(cinemaDAO.update(cinema));
        assertEquals(cinema.getName(),
                cinemaDAO.find(cinema.getId()).getName());
    }

    @Test
    public void deleteCinema() {
        Cinema cinema = new Cinema("name", "location");
        cinemaDAO.add(cinema);

        assertTrue(cinemaDAO.delete(cinema.getId()));
        assertNull(cinemaDAO.find(cinema.getId()));
    }
}
