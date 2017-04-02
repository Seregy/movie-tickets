package cinema;

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

import static org.junit.Assert.*;

/**
 * Created by Shera on 31.03.2017.
 */
public class HibernateTest {
    @Test
    public void addTest() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();



        Cinema cinema = new Cinema("Favorite","somewhere");
        Hall hall = new Hall("black",cinema);
        cinema.addHall(hall);


        Movie movie = new Movie("Logan");
        Session session = new Session(LocalDateTime.now(),movie,hall);
       // User user = new User("","","","","");
       // Ticket ticket = new Ticket(user,session,1,2);


        hall.addSession(session);
        movie.addSession(session);


        entityManager.persist(cinema);
        entityManager.persist(movie);

        entityManager.getTransaction().commit();




        entityManager.getTransaction().begin();



        Cinema foundCinema = entityManager.find(Cinema.class, cinema.getId());
        Set<Hall> allHalls = foundCinema.getHalls();

        entityManager.getTransaction().commit();
        entityManager.close();
        emf.close();



    }

}