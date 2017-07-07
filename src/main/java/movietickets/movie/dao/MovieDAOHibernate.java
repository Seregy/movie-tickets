package movietickets.movie.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.movie.Movie;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Movie}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class MovieDAOHibernate
        extends AbstractDAOHibernate<Movie> implements MovieDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Movie> getEntityClass() {
        return Movie.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Movie";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return "screeningDate";
    }
}
