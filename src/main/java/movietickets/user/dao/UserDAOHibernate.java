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
public final class UserDAOHibernate
        extends AbstractDAOHibernate<User> implements UserDAO {
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public String getEntityName() {
        return "User";
    }

    @Override
    public String getOrderColumn() {
        return "name";
    }
}
