package user.dao;

import core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Service;
import user.User;

import javax.transaction.Transactional;

/**
 * Data access object for {@link User}, uses Hibernate.
 *
 * @author Seregy
 */
@Service
@Transactional
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
}