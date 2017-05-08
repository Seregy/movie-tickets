package movietickets.hall.layout.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.hall.layout.Layout;

/**
 * Data access object for {@link Layout}, uses Hibernate.
 *
 * @author Seregy
 */
public final class LayoutDAOHibernate
        extends AbstractDAOHibernate<Layout> implements LayoutDAO {
    @Override
    public Class<Layout> getEntityClass() {
        return Layout.class;
    }

    @Override
    public String getEntityName() {
        return "Layout";
    }
}
