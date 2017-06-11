package movietickets.user.permission.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.user.permission.Permission;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link Permission}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public final class PermissionDAOHibernate
        extends AbstractDAOHibernate<Permission> implements PermissionDAO {
    @Override
    public Class<Permission> getEntityClass() {
        return Permission.class;
    }

    @Override
    public String getEntityName() {
        return "Permission";
    }

    @Override
    public String getOrderColumn() {
        return "name";
    }
}
