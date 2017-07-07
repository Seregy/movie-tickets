package movietickets.session.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.session.Session;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Session}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class SessionDAOHibernate
        extends AbstractDAOHibernate<Session> implements SessionDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Session> getEntityClass() {
        return Session.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Session";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return "sessionStart";
    }
}
