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
public class PermissionDAOHibernate
        extends AbstractDAOHibernate<Permission> implements PermissionDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Permission> getEntityClass() {
        return Permission.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Permission";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return "name";
    }
}
