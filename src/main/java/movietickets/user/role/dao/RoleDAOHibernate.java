package movietickets.user.role.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.user.role.Role;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Role}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public final class RoleDAOHibernate
        extends AbstractDAOHibernate<Role> implements RoleDAO {
    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public String getEntityName() {
        return "Role";
    }

    @Override
    public String getOrderColumn() {
        return "name";
    }
}
