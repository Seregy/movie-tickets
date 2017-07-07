package movietickets.cinema.dao;

import movietickets.cinema.Cinema;
import movietickets.core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Cinema}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class CinemaDAOHibernate
        extends AbstractDAOHibernate<Cinema> implements CinemaDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Cinema> getEntityClass() {
        return Cinema.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Cinema";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return "name";
    }
}
