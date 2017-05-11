package movietickets.movie.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.movie.Movie;

/**
 * Data access object for {@link Movie}, uses Hibernate.
 *
 * @author Seregy
 */
public final class MovieDAOHibernate
        extends AbstractDAOHibernate<Movie> implements MovieDAO {
    @Override
    public Class<Movie> getEntityClass() {
        return Movie.class;
    }

    @Override
    public String getEntityName() {
        return "Movie";
    }
}
