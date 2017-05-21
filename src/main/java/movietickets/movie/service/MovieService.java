package movietickets.movie.service;

import movietickets.movie.Movie;

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
     * Gets all existing movies as list.
     *
     * @return list of movies
     */
    List<Movie> getAll();

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
}
