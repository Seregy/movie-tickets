package movietickets.hall.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.hall.Hall;

/**
 * Data access object for {@link Hall}, uses Hibernate.
 *
 * @author Seregy
 */
public final class HallDAOHibernate
        extends AbstractDAOHibernate<Hall> implements HallDAO {
    @Override
    public Class<Hall> getEntityClass() {
        return Hall.class;
    }

    @Override
    public String getEntityName() {
        return "Hall";
    }
}
