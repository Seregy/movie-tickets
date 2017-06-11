package movietickets.seat.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.seat.Seat;

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

    @Override
    public String getOrderColumn() {
        return null;
    }
}
