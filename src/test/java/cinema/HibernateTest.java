package cinema;

import cinema.dao.CinemaDAOHibernate;
import core.dao.AbstractDAOHibernate;
import hall.Hall;
import movie.Movie;
import org.junit.Test;
import session.Session;
import ticket.Ticket;
import user.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Shera on 31.03.2017.
 */
public class HibernateTest {
    @Test
    public void addTest() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinemaDB");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Cinema cinema = new Cinema("Favorite","somewhere");
        Hall hall = new Hall("black");
        Session session = new Session(LocalDateTime.now());
        Movie movie = new Movie("Logan");

        cinema.addHall(hall);
        entityManager.persist(cinema);

        movie.addSession(session);
        hall.addSession(session);
        entityManager.persist(movie);

        entityManager.getTransaction().commit();




        entityManager.getTransaction().begin();



        Cinema foundCinema = entityManager.find(Cinema.class, cinema.getId());

        Set<Hall> allHalls = foundCinema.getHalls();

        entityManager.getTransaction().commit();
        entityManager.close();
        emf.close();

        CinemaDAOHibernate cinemaDAOHibernate = new CinemaDAOHibernate();

        cinemaDAOHibernate.find(cinema.getId());
        cinemaDAOHibernate.delete(UUID.randomUUID());
        cinema.setName("NEEEEW");
        cinemaDAOHibernate.findAll();

        cinemaDAOHibernate.update(cinema);
    }

}