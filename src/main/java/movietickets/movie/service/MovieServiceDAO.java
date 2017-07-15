package movietickets.movie.service;

import movietickets.city.City;
import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Movie's service object, uses DAO.
 *
 * @author Seregy
 */
@Service
public class MovieServiceDAO implements MovieService {
    private final MovieDAO movieDAO;

    /**
     * Constructs new movie service with given Movie DAO.
     *
     * @param movieDAO movie data access object
     */
    @Autowired
    public MovieServiceDAO(final MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movie, 'add')")
    @Transactional
    @Override
    public void add(@P("movie") final Movie movie) {
        movieDAO.add(movie);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Movie get(final UUID id) {
        return movieDAO.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Movie> getAll() {
        return movieDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Movie> getAllAvailable() {
        return movieDAO.findAll().stream()
                .filter(movie -> movie.getScreeningDate()
                        .isAfter(LocalDate.now())
                        || movie.getSessions().stream()
                                .anyMatch(session -> session.getSessionStart()
                                        .isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<Movie> getAllAvailable(final City city) {
        return movieDAO.findAll().stream()
                .filter(movie -> movie.getScreeningDate()
                        .isAfter(LocalDate.now())
                        || movie.getSessions().stream()
                        .anyMatch(session -> session.getSessionStart()
                                .isAfter(LocalDateTime.now())
                                && session.getHall().getCinema()
                                .getCity().equals(city)))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#id, 'Movie', 'delete')")
    @Transactional
    @Override
    public void delete(@P("id") final UUID id) {
        movieDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeName(@P("movieId") final UUID movieId,
                           final String newName) {
        Movie movie = movieDAO.find(movieId);
        movie.setName(newName);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeDuration(@P("movieId") final UUID movieId,
                               final int newDuration) {
        Movie movie = movieDAO.find(movieId);
        movie.setDuration(newDuration);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeAnnotation(@P("movieId") final UUID movieId,
                                 final String newAnnotation) {
        Movie movie = movieDAO.find(movieId);
        movie.setAnnotation(newAnnotation);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeYear(@P("movieId") final UUID movieId,
                           final int newYear) {
        Movie movie = movieDAO.find(movieId);
        movie.setYear(newYear);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeCountry(@P("movieId") final UUID movieId,
                              final String newCountry) {
        Movie movie = movieDAO.find(movieId);
        movie.setCountry(newCountry);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeGenres(@P("movieId") final UUID movieId,
                             final String newGenres) {
        Movie movie = movieDAO.find(movieId);
        movie.setGenres(newGenres);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeCast(@P("movieId") final UUID movieId,
                           final String newCast) {
        Movie movie = movieDAO.find(movieId);
        movie.setCast(newCast);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeDirector(@P("movieId") final UUID movieId,
                               final String newDirector) {
        Movie movie = movieDAO.find(movieId);
        movie.setDirector(newDirector);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeScreeningDate(@P("movieId") final UUID movieId,
                                    final LocalDate newDate) {
        Movie movie = movieDAO.find(movieId);
        movie.setScreeningDate(newDate);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changePremiereEndDate(@P("movieId") final UUID movieId,
                                      final LocalDate newDate) {
        Movie movie = movieDAO.find(movieId);
        movie.setPremiereEndDate(newDate);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasPermission(#movieId, 'Movie', 'edit')")
    @Transactional
    @Override
    public void changeContentRating(@P("movieId") final UUID movieId,
                                    final String newRating) {
        Movie movie = movieDAO.find(movieId);
        movie.setContentRating(newRating);
        movieDAO.update(movie);
    }
}
