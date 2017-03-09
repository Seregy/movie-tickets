package cinema;

import core.DAO;

import static org.junit.Assert.*;

/**
 * Tests for CinemaDAODefault and CinemaDAOMongo.
 */
public class CinemaDAOTest {
    private final Cinema[] cinemas = { new Cinema(1, "First", "FirstLocation"),
            new Cinema(2, "Second", "SecondLocation"),
            new Cinema(3, "Third", "ThirdLocation"),
            new Cinema(4, "Fourth", "FourthLocation"),
            new Cinema(5, "Multiplex", "MultiplexLocation1"),
            new Cinema(6, "Multiplex2", "MultiplexLocation2"),
            new Cinema(7, "Multiplex3", "MultiplexLocation3"),
            new Cinema(8, "Multiplex4", "MultiplexLocation4"),
            new Cinema(9, "Multiplex5", "MultiplexLocation5")
    };

    private DAO<Cinema, Integer> daoDefault = new CinemaDAODefault();
    private DAO<Cinema, Integer> daoMongo = new CinemaDAOMongo();

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

    private void find(DAO<Cinema, Integer> cinemaDAO) {
        assertEquals(cinemas[2], cinemaDAO.find(3));
    }

    private void findAll(DAO<Cinema, Integer> cinemaDAO) {
        assertEquals(cinemas.length, cinemaDAO.findAll().size());
    }

    private void add(DAO<Cinema, Integer> cinemaDAO) {
        for (Cinema cinema : cinemas) {
            cinemaDAO.add(cinema);
        }
    }

    private void update(DAO<Cinema, Integer> cinemaDAO) {
        cinemaDAO.update(new Cinema(2, "SecondChanged", "SecondLocation"));
        assertNotEquals(cinemas[1].getName(), cinemaDAO.find(2).getName());
        cinemaDAO.update(new Cinema(5, "FifthChanged", "MultiplexLocation1Changed"));
        assertNotEquals(cinemas[4].getLocation(), cinemaDAO.find(2).getLocation());
    }

    private void delete(DAO<Cinema, Integer> cinemaDAO) {
        for (Cinema cinema : cinemas) {
            cinemaDAO.delete(cinema.getId());
        }
    }

}