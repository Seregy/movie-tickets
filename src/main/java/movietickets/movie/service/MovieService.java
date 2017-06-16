package movietickets.movie.service;

import movietickets.city.City;
import movietickets.movie.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Movie's service object, used for applying business logic.
 *
 * @author Seregy
 */
public interface MovieService {
    /**
     * Adds new movie.
     *
     * @param movie new movie
     */
    void add(Movie movie);

    /**
     * Gets movie with specified id.
     *
     * @param id movie's id
     * @return movie with specified id
     * or {@code null} if it wasn't found
     */
    Movie get(UUID id);

    /**
     * Gets all existing movies as a list.
     *
     * @return list of movies
     */
    List<Movie> getAll();

    /**
     * Gets all available movies as a list.
     * Movie is considered available if its either 'coming soon'
     * (screening date is in the future) or it has
     * sessions in the future.
     *
     * @return list of available movies
     */
    List<Movie> getAllAvailable();

    /**
     * Gets all available(in specified city) movies as a list.
     * Movie is considered available if its either 'coming soon'
     * (screening date is in the future) or it has
     * sessions in the future in specified city.
     *
     * @param city city to search movies in
     * @return list of available movies
     */
    List<Movie> getAllAvailable(City city);

    /**
     * Deletes movie.
     *
     * @param movie movie to delete
     */
    void delete(Movie movie);

    /**
     * Deletes movie.
     *
     * @param id id of the movie to delete
     */
    void delete(UUID id);

    /**
     * Changes the name of the movie.
     *
     * @param movieId movie's id
     * @param newName new name
     */
    void changeName(UUID movieId, String newName);

    /**
     * Changes the duration of the movie(in minutes).
     *
     * @param movieId movie's id
     * @param newDuration new duration in minutes
     */
    void changeDuration(UUID movieId, int newDuration);

    /**
     * Changes the annotation of the movie.
     *
     * @param movieId movie's id
     * @param newAnnotation new annotation
     */
    void changeAnnotation(UUID movieId, String newAnnotation);

    /**
     * Changes movie's release year.
     *
     * @param movieId movie's id
     * @param newYear new year
     */
    void changeYear(UUID movieId, int newYear);

    void changeCountry(UUID movieId, String newCountry);

    void changeGenres(UUID movieId, String newGenres);

    void changeCast(UUID movieId, String newCast);

    void changeDirector(UUID movieId, String newDirector);

    void changeScreeningDate(UUID movieId, LocalDate newDate);

    void changePremiereEndDate(UUID movieId, LocalDate newDate);

    void changeContentRating(UUID movieId, String newRating);

    void changePathToPoster(UUID movieId, String newPath);
}
