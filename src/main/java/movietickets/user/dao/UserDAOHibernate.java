package movietickets.user.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Repository;
import movietickets.user.User;

/**
 * Data access object for {@link User}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class UserDAOHibernate
        extends AbstractDAOHibernate<User> implements UserDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "User";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return "name";
    }
}
