package movietickets.movie.web;


import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Movie controller, responsible for giving pages and
 * resources related to movie.
 *
 * @author Seregy
 */
@Controller
public class MovieController {
    private final MovieService movieService;

    /**
     * Constructs new movie controller.
     *
     * @param movieService movie service
     */
    @Autowired
    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Shows movie page with information about movie.
     *
     * @param id movie's id
     * @return name of jsp-page
     */
    @GetMapping("/movie/{id}")
    public ModelAndView showMovie(@PathVariable("id")
                                    final String id) {
        ModelAndView modelAndView = new ModelAndView("movie");
        Movie movie = movieService.get(UUID.fromString(id));
        modelAndView.addObject("movie", movie);
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
        movieService.add(movie);
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
        movieService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
