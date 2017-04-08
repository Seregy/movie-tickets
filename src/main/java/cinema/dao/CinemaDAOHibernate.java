package cinema.dao;

import cinema.Cinema;
import core.dao.AbstractDAOHibernate;
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
}
