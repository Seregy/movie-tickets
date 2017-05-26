package movietickets.user.role.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import movietickets.user.role.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by Seregy on 26.05.2017.
 */
@Repository
public class RoleDAOHibernate extends AbstractDAOHibernate<Role> implements RoleDAO {
    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public String getEntityName() {
        return "Role";
    }
}
