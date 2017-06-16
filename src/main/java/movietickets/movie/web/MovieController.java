package movietickets.movie.web;


import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
     * Shows list of all movies for admin panel.
     *
     * @param name movie's name filter
     * @return model and view
     */
    @GetMapping("/admin/movies")
    public ModelAndView showAdminMovies(@RequestParam(value = "name",
                                                        required = false)
                                            final String name) {
        ModelAndView modelAndView =
                new ModelAndView("fragments/admin/movie_block");
        List<Movie> movies = movieService.getAll();
        if (name != null) {
            movies = movies.stream()
                    .filter(m -> m.getName().contains(name))
                    .collect(Collectors.toList());
        }

        modelAndView.addObject("movies", movies);
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
    @PostMapping("/movie")
    public ResponseEntity addMovie(@RequestParam("name")
                                       final String name,
                                   @RequestParam("annotation")
                                       final String annotation,
                                   @RequestParam("year")
                                       final int year,
                                   @RequestParam("country")
                                       final String country,
                                   @RequestParam("genres")
                                       final String genres,
                                   @RequestParam("cast")
                                       final String cast,
                                   @RequestParam("director")
                                       final String director,
                                   @RequestParam("screeningDate")
                                       final String screeningDate,
                                   @RequestParam("premiereEndDate")
                                       final String premiereEndDate,
                                   @RequestParam("duration")
                                       final int duration,
                                   @RequestParam("contentRating")
                                       final String contentRating) {
        Movie movie = new Movie(name, duration, annotation);
        movie.setYear(year);
        movie.setCountry(country);
        movie.setGenres(genres);
        movie.setCast(cast);
        movie.setDirector(director);
        movie.setScreeningDate(LocalDate.parse(screeningDate));
        movie.setPremiereEndDate(LocalDate.parse(premiereEndDate));
        movie.setContentRating(contentRating);
        movie.setPathToPoster("no.png");
        movieService.add(movie);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing movie.
     *
     * @param id movie's id
     * @return response entity
     */
    @PostMapping("/movie/{id}")
    public ResponseEntity editMovie(@PathVariable("id")
                                        final UUID id,
                                    @RequestParam("name")
                                        final String name,
                                    @RequestParam("annotation")
                                        final String annotation,
                                    @RequestParam("year")
                                        final int year,
                                    @RequestParam("country")
                                        final String country,
                                    @RequestParam("genres")
                                        final String genres,
                                    @RequestParam("cast")
                                        final String cast,
                                    @RequestParam("director")
                                        final String director,
                                    @RequestParam("screeningDate")
                                        final String screeningDate,
                                    @RequestParam("premiereEndDate")
                                        final String premiereEndDate,
                                    @RequestParam("duration")
                                        final int duration,
                                    @RequestParam("contentRating")
                                        final String contentRating) {
        movieService.changeName(id, name);
        movieService.changeAnnotation(id, annotation);
        movieService.changeYear(id, year);
        movieService.changeCountry(id, country);
        movieService.changeGenres(id, genres);
        movieService.changeCast(id, cast);
        movieService.changeDirector(id, director);
        movieService.changeScreeningDate(id, LocalDate.parse(screeningDate));
        movieService.changePremiereEndDate(id, LocalDate.parse(premiereEndDate));
        movieService.changeDuration(id, duration);
        movieService.changeContentRating(id, contentRating);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes movie with given id.
     *
     * @param id identifier of the movie
     * @return response code
     */
    @DeleteMapping("/movie/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") final String id) {
        movieService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
