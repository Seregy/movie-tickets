package seat.dao;

import core.dao.AbstractDAOHibernate;
import seat.Seat;

/**
 * Data access object for {@link Seat}, uses Hibernate.
 *
 * @author Seregy
 */
public final class SeatDAOHibernate
        extends AbstractDAOHibernate<Seat> implements SeatDAO {
    @Override
    public Class<Seat> getEntityClass() {
        return Seat.class;
    }

    @Override
    public String getEntityName() {
        return "Seat";
    }
}
