package movietickets.movie.web;


import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Movie controller, responsible for giving pages and
 * resources related to movie.
 *
 * @author Seregy
 */
@Controller
@Transactional
public class MovieController {

    private final MovieDAO movieDAO;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs new movie controller with given Movie DAO.
     *
     * @param movieDAO movie data access object
     */
    @Autowired
    public MovieController(final MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }


    /**
     * Shows main movies' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/movie")
    public String showMoviesPages() {
        return "movie";
    }

    /**
     * Shows table, filled with movies.
     *
     * @return name of jsp-page
     */
    @GetMapping("/movies")
    public ModelAndView showMovies() {
        ModelAndView modelAndView = new ModelAndView("movies_table");
        modelAndView.addObject("movies", movieDAO.findAll());
        return modelAndView;
    }

    /**
     * Adds new movie with given name, duration and annotation.
     *
     * @param name movie's title
     * @param duration movie's duration in minutes
     * @param annotation movie's annotation
     * @return response code
     */
    @PostMapping("/movies")
    public ResponseEntity addMovie(@RequestParam("name")
                                  final String name,
                                   @RequestParam("duration")
                                   final int duration,
                                   @RequestParam("annotation")
                                   final String annotation) {
        Movie movie = new Movie(name, duration, annotation);
        movieDAO.add(movie);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes movie with given id.
     *
     * @param id identifier of the movie
     * @return response code
     */
    @DeleteMapping("/movies/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") final String id) {
        movieDAO.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
