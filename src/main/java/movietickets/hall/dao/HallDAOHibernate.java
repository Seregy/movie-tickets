package movietickets.hall.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.hall.Hall;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Hall}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class HallDAOHibernate
        extends AbstractDAOHibernate<Hall> implements HallDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Hall> getEntityClass() {
        return Hall.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Hall";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return null;
    }
}
