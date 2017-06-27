package movietickets.movie.web;


import movietickets.core.service.StorageService;
import movietickets.movie.Movie;
import movietickets.movie.service.MovieService;
import movietickets.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
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
@SessionAttributes("currentCity")
public class MovieController {
    private static final String POSTER_FOLDER_PATH = "images/movies/";
    private static final String NO_POSTER_FILE = "no-image";

    private final MovieService movieService;
    private final StorageService storageService;
    private final SessionService sessionService;

    /**
     * Constructs new movie controller.
     *
     * @param movieService movie service
     * @param storageService storage service
     * @param sessionService session service
     */
    @Autowired
    public MovieController(final MovieService movieService,
                           final StorageService storageService,
                           final SessionService sessionService) {
        this.movieService = movieService;
        this.storageService = storageService;
        this.sessionService = sessionService;
    }

    /**
     * Shows movie page with information about movie.
     *
     * @param id movie's id
     * @return name of jsp-page
     */
    @GetMapping("/movie/{id}")
    public ModelAndView showMovie(@PathVariable("id")
                                    final UUID id) {
        ModelAndView modelAndView = new ModelAndView("movie");
        Movie movie = movieService.get(id);
        modelAndView.addObject("movie", movie);
        Set<String> technology = new HashSet<>();
        sessionService.getAllFuture(id)
                .forEach(session -> technology.add(session.getTechnology()));
        modelAndView.addObject("displayTechnologies", technology);
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
     * @param name movie's name
     * @param duration movie's duration in minutes
     * @param annotation movie's annotation
     * @param year movie's release year
     * @param country movie's country of creation
     * @param genres movie's genres
     * @param cast movie's cast
     * @param director movie's director
     * @param screeningDate movie's screening date
     * @param premiereEndDate end date of the movie's premiere period
     * @param contentRating movie's content rating
     * @param poster movie's poster image
     * @return response
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    @PostMapping(value = "/movie")
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
                                       final String contentRating,
                                   @RequestPart("poster")
                                       final MultipartFile poster) {
        Movie movie = new Movie(name, duration, annotation);
        movie.setYear(year);
        movie.setCountry(country);
        movie.setGenres(genres);
        movie.setCast(cast);
        movie.setDirector(director);
        movie.setScreeningDate(LocalDate.parse(screeningDate));
        movie.setPremiereEndDate(LocalDate.parse(premiereEndDate));
        movie.setContentRating(contentRating);
        movieService.add(movie);

        String movieId = movie.getId().toString();
        if (poster.isEmpty()) {
            File file = storageService.loadAsFile(NO_POSTER_FILE,
                    POSTER_FOLDER_PATH);
            storageService.store(file, movieId, POSTER_FOLDER_PATH);
        } else {
            storageService.store(poster, movieId, POSTER_FOLDER_PATH);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing movie.
     *
     * @param id movie's id
     * @param name movie's new name
     * @param duration movie's new duration in minutes
     * @param annotation movie's new annotation
     * @param year movie's new release year
     * @param country movie's new country of creation
     * @param genres movie's new genres
     * @param cast movie's new cast
     * @param director movie's new director
     * @param screeningDate movie's new screening date
     * @param premiereEndDate new end date of the movie's premiere period
     * @param contentRating movie's new content rating
     * @return response
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
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
        movieService.changePremiereEndDate(id,
                LocalDate.parse(premiereEndDate));
        movieService.changeDuration(id, duration);
        movieService.changeContentRating(id, contentRating);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Edits existing movie's poster.
     *
     * @param id movie's id
     * @param poster movie's new poster
     * @return response
     */
    @PostMapping("/movie/{id}/poster")
    public ResponseEntity editMoviePoster(@PathVariable("id")
                                              final UUID id,
                                          @RequestPart("poster")
                                              final MultipartFile poster) {
        storageService.store(poster, id.toString(), POSTER_FOLDER_PATH);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes movie with given id.
     *
     * @param id identifier of the movie
     * @return response code
     */
    @DeleteMapping("/movie/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") final UUID id) {
        movieService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Gets movie poster.
     *
     * @param id movie's id
     * @return poster as bytes
     */
    @GetMapping(value = "/movie/{id}/poster")
    @ResponseBody
    public byte[] getMoviePoster(@PathVariable final UUID id)  {
        return storageService.loadAsByteArray(id.toString(),
                POSTER_FOLDER_PATH);
    }
}
