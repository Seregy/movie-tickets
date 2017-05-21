package movietickets.movie.service;

import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public void delete(final Movie movie) {
        delete(movie.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(final UUID id) {
        movieDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
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
    @Transactional
    @Override
    public void changeAnnotation(final UUID movieId,
                                 final String newAnnotation) {
        Movie movie = movieDAO.find(movieId);
        movie.setAnnotation(newAnnotation);
        movieDAO.update(movie);
    }
}
