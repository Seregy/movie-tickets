package test.java.cinema;

import main.java.cinema.Cinema;
import main.java.cinema.CinemaDAO;
import main.java.cinema.CinemaDAODefault;
import main.java.cinema.CinemaDAOMongo;

/**
 * Tests for CinemaDAODefault and CinemaDAOMongo.
 */
public class CinemaDAOTest {
    private CinemaDAO daoDefault = new CinemaDAODefault();
    private CinemaDAO daoMongo = new CinemaDAOMongo();

    @org.junit.Test
    public void testDefault() throws Exception {
        add(daoDefault);
        update(daoDefault);
        find(daoDefault);
        delete(daoDefault);
        findAll(daoDefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(daoMongo);
        update(daoMongo);
        find(daoMongo);
        delete(daoMongo);
        findAll(daoMongo);
    }

    private void find(CinemaDAO cinemaDAO) throws Exception {
        System.out.println(cinemaDAO.find(3));
        System.out.println(cinemaDAO.find("Multiplex"));
    }

    private void findAll(CinemaDAO cinemaDAO) throws Exception {
        System.out.println(cinemaDAO.findAll());
    }

    private void add(CinemaDAO cinemaDAO) throws Exception {
        cinemaDAO.add(new Cinema(1, "First", "FirstLocation"));
        cinemaDAO.add(new Cinema(2, "Second", "SecondLocation"));
        cinemaDAO.add(new Cinema(3, "Third", "ThirdLocation"));
        cinemaDAO.add(new Cinema(4, "Fourth", "FourthLocation"));
        cinemaDAO.add(new Cinema(5, "Multiplex", "MultiplexLocation1"));
        cinemaDAO.add(new Cinema(6, "Multiplex", "MultiplexLocation2"));
        cinemaDAO.add(new Cinema(7, "Multiplex", "MultiplexLocation3"));
        cinemaDAO.add(new Cinema(8, "Multiplex", "MultiplexLocation4"));
        cinemaDAO.add(new Cinema(9, "Multiplex", "MultiplexLocation5"));
    }

    private void update(CinemaDAO cinemaDAO) throws Exception {
        cinemaDAO.update(new Cinema(2, "SecondChanged", "SecondLocation"));
        cinemaDAO.update(new Cinema(5, "FifthChanged", "MultiplexLocation1Changed"));
    }

    private void delete(CinemaDAO cinemaDAO) throws Exception {
        cinemaDAO.delete(new Cinema(8, "Multiplex", "MultiplexLocation4"));
        cinemaDAO.delete(new Cinema(9, "Multiplex", "MultiplexLocation5"));
    }

}