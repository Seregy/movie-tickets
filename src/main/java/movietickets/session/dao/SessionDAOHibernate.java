package movietickets.session.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.session.Session;

/**
 * Data access object for {@link Session}, uses Hibernate.
 *
 * @author Seregy
 */
public final class SessionDAOHibernate
        extends AbstractDAOHibernate<Session> implements SessionDAO {
    @Override
    public Class<Session> getEntityClass() {
        return Session.class;
    }

    @Override
    public String getEntityName() {
        return "Session";
    }

    @Override
    public String getOrderColumn() {
        return "sessionStart";
    }
}
