package movietickets.movie.dao;

import movietickets.movie.Movie;
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
 * Tests for Movie data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class MovieDAOTest {
    @Autowired
    private MovieDAO movieDAO;

    @Test
    public void findMovie() {
        Movie[] movies = {
                new Movie("first", 1, "first"),
                new Movie("second", 2, "second"),
                new Movie("third", 3, "third")};
        for (Movie movie : movies) {
            movieDAO.add(movie);
        }

        assertEquals(movies[1], movieDAO.find(movies[1].getId()));
    }

    @Test
    public void findAllMovies() {
        Movie[] movies = {
                new Movie("first", 1, "first"),
                new Movie("second", 2, "second"),
                new Movie("third", 3, "third"),
                new Movie("fourth", 4, "fourth")};
        for (Movie movie : movies) {
            movieDAO.add(movie);
        }

        assertArrayEquals(movies, movieDAO.findAll().toArray(new Movie[0]));
    }

    @Test
    public void addMovie() {
        Movie movie = new Movie("name", 100, "annotation");

        assertTrue(movieDAO.add(movie));
        assertEquals(movie, movieDAO.find(movie.getId()));
    }

    @Test
    public void updateMovie() {
        Movie movie = new Movie("name", 100, "annotation");
        movieDAO.add(movie);
        movie.setName("new name");

        assertTrue(movieDAO.update(movie));
        assertEquals(movie.getName(),
                movieDAO.find(movie.getId()).getName());
    }

    @Test
    public void deleteMovie() {
        Movie movie = new Movie("name", 100, "annotation");
        movieDAO.add(movie);

        assertTrue(movieDAO.delete(movie.getId()));
        assertNull(movieDAO.find(movie.getId()));
    }
}
