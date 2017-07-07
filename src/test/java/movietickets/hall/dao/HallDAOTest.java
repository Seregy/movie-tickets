package movietickets.hall.dao;

import movietickets.hall.Hall;
import movietickets.hall.layout.Layout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Tests for Hall data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class HallDAOTest {
    @Autowired
    private HallDAO hallDAO;

    @Test
    public void findHall() {
        Hall[] halls = {
                new Hall("First"),
                new Hall("Second"),
                new Hall("Third")};
        for (Hall hall : halls) {
            hall.setLayout(new Layout(5, 5));
        }

        for (Hall hall : halls) {
            hallDAO.add(hall);
        }

        assertEquals(halls[1], hallDAO.find(halls[1].getId()));
    }

    @Test
    public void findAllHalls() {
        Hall[] halls = {new Hall("First"),
                new Hall("Second"),
                new Hall("Third"),
                new Hall("Fourth")};
        for (Hall hall : halls) {
            hall.setLayout(new Layout(5, 5));
        }

        for (Hall hall : halls) {
            hallDAO.add(hall);
        }

        assertArrayEquals(halls, hallDAO.findAll().toArray(new Hall[0]));
    }

    @Test
    public void addHall() {
        Hall hall = new Hall("name");
        hall.setLayout(new Layout(1, 1));

        assertTrue(hallDAO.add(hall));
        assertEquals(hall, hallDAO.find(hall.getId()));
    }

    @Test
    public void updateHall() {
        Hall hall = new Hall("name");
        hall.setLayout(new Layout(1, 1));
        hallDAO.add(hall);
        hall.setName("another name");

        assertTrue(hallDAO.update(hall));
        assertEquals(hall.getName(),
                hallDAO.find(hall.getId()).getName());
    }

    @Test
    public void deleteHall() {
        Hall hall = new Hall("name");
        hall.setLayout(new Layout(1, 1));
        hallDAO.add(hall);

        assertTrue(hallDAO.delete(hall.getId()));
        assertNull(hallDAO.find(hall.getId()));
    }
}
