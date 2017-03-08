package cinema;

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
        assertEquals(cinemas[2], cinemaDAO.find(3));
        assertEquals(cinemas[4], cinemaDAO.find("Multiplex").get(0));
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
        cinemaDAO.update(new Cinema(2, "SecondChanged", "SecondLocation"));
        assertNotEquals(cinemas[1].getName(), cinemaDAO.find(2).getName());
        cinemaDAO.update(new Cinema(5, "FifthChanged", "MultiplexLocation1Changed"));
        assertNotEquals(cinemas[4].getLocation(), cinemaDAO.find(2).getLocation());
    }

    private void delete(CinemaDAO cinemaDAO) {
        for (Cinema cinema : cinemas) {
            cinemaDAO.delete(cinema);
        }
    }

}