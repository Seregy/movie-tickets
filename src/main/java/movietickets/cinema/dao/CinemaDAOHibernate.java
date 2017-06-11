package movietickets.cinema.dao;

import movietickets.cinema.Cinema;
import movietickets.core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Service;

/**
 * Data access object for {@link Cinema}, uses Hibernate.
 *
 * @author Seregy
 */
@Service
public final class CinemaDAOHibernate
        extends AbstractDAOHibernate<Cinema> implements CinemaDAO {

    @Override
    public Class<Cinema> getEntityClass() {
        return Cinema.class;
    }

    @Override
    public String getEntityName() {
        return "Cinema";
    }

    @Override
    public String getOrderColumn() {
        return "name";
    }
}
