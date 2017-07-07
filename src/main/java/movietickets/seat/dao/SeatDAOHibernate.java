package movietickets.seat.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.seat.Seat;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Seat}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class SeatDAOHibernate
        extends AbstractDAOHibernate<Seat> implements SeatDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Seat> getEntityClass() {
        return Seat.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Seat";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return null;
    }
}
