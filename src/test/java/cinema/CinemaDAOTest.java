package cinema;

import cinema.dao.CinemaDAO;
import cinema.dao.CinemaDAODefault;
import cinema.dao.CinemaDAOMongo;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Tests for CinemaDAODefault and CinemaDAOMongo.
 */
public class CinemaDAOTest {
    private final Cinema[] cinemas = { new Cinema(UUID.randomUUID(), "First", "FirstLocation"),
            new Cinema(UUID.randomUUID(), "Second", "SecondLocation"),
            new Cinema(UUID.randomUUID(), "Third", "ThirdLocation"),
            new Cinema(UUID.randomUUID(), "Fourth", "FourthLocation"),
            new Cinema(UUID.randomUUID(), "Multiplex", "MultiplexLocation1"),
            new Cinema(UUID.randomUUID(), "Multiplex2", "MultiplexLocation2"),
            new Cinema(UUID.randomUUID(), "Multiplex3", "MultiplexLocation3"),
            new Cinema(UUID.randomUUID(), "Multiplex4", "MultiplexLocation4"),
            new Cinema(UUID.randomUUID(), "Multiplex5", "MultiplexLocation5")
    };

    private CinemaDAO daoDefault = new CinemaDAODefault();
    private CinemaDAO daoMongo = new CinemaDAOMongo();

    @org.junit.Test
    public void testDefault() throws Exception {
        add(daoDefault);
        find(daoDefault);
        update(daoDefault);
        findAll(daoDefault);
        delete(daoDefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(daoMongo);
        find(daoMongo);
        findAll(daoMongo);
        update(daoMongo);
        delete(daoMongo);
    }

    private void find(CinemaDAO cinemaDAO) {
        assertEquals(cinemas[2], cinemaDAO.find(cinemas[2].getId()));
    }

    private void findAll(CinemaDAO cinemaDAO) {
        assertEquals(cinemas.length, cinemaDAO.findAll().size());
    }

    private void add(CinemaDAO cinemaDAO) {
        for (Cinema cinema : cinemas) {
            cinemaDAO.add(cinema);
        }
    }

    private void update(CinemaDAO cinemaDAO) {
        cinemaDAO.update(new Cinema(cinemas[1].getId(), "SecondChanged", "SecondLocation"));
        assertNotEquals(cinemas[1].getName(), cinemaDAO.find(cinemas[1].getId()).getName());
        cinemaDAO.update(new Cinema(cinemas[4].getId(), "FifthChanged", "MultiplexLocation1Changed"));
        assertNotEquals(cinemas[4].getLocation(), cinemaDAO.find(cinemas[4].getId()).getLocation());
    }

    private void delete(CinemaDAO cinemaDAO) {
        for (Cinema cinema : cinemas) {
            cinemaDAO.delete(cinema.getId());
        }
    }

}