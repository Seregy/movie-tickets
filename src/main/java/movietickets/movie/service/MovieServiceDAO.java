package movietickets.movie.service;

import movietickets.city.City;
import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PreAuthorize("hasAuthority('PM_ADD')")
    @Transactional
    @Override
    public void add(final Movie movie) {
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
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final Movie movie) {
        delete(movie.getId());
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_DELETE')")
    @Transactional
    @Override
    public void delete(final UUID id) {
        movieDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeName(final UUID movieId, final String newName) {
        Movie movie = movieDAO.find(movieId);
        movie.setName(newName);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeDuration(final UUID movieId, final int newDuration) {
        Movie movie = movieDAO.find(movieId);
        movie.setDuration(newDuration);
        movieDAO.update(movie);
    }

    /**
     * {@inheritDoc}
     */
    @PreAuthorize("hasAuthority('PM_EDIT')")
    @Transactional
    @Override
    public void changeAnnotation(final UUID movieId,
                                 final String newAnnotation) {
        Movie movie = movieDAO.find(movieId);
        movie.setAnnotation(newAnnotation);
        movieDAO.update(movie);
    }
}
