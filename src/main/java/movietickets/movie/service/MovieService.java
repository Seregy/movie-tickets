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

    /**
     * Changes movie's country of creation.
     *
     * @param movieId movie's id
     * @param newCountry new country
     */
    void changeCountry(UUID movieId, String newCountry);

    /**
     * Changes movie's genres.
     *
     * @param movieId movie's id
     * @param newGenres new genres
     */
    void changeGenres(UUID movieId, String newGenres);

    /**
     * Changes movie's cast.
     *
     * @param movieId movie's id
     * @param newCast new cast
     */
    void changeCast(UUID movieId, String newCast);

    /**
     * Changes movie's director.
     *
     * @param movieId movie's id
     * @param newDirector new director
     */
    void changeDirector(UUID movieId, String newDirector);

    /**
     * Changes movie's screening date.
     *
     * @param movieId movie's id
     * @param newDate new screening date
     */
    void changeScreeningDate(UUID movieId, LocalDate newDate);

    /**
     * Changes movie's end date of the movie's premiere period.
     *
     * @param movieId movie's id
     * @param newDate new end date
     */
    void changePremiereEndDate(UUID movieId, LocalDate newDate);

    /**
     * Changes movie's content rating.
     *
     * @param movieId movie's id
     * @param newRating new content rating
     */
    void changeContentRating(UUID movieId, String newRating);
}
