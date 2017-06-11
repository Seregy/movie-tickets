package movietickets.city.dao;

import movietickets.city.City;
import movietickets.core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link City}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public final class CityDAOHibernate
        extends AbstractDAOHibernate<City> implements CityDAO {
    @Override
    public Class<City> getEntityClass() {
        return City.class;
    }

    @Override
    public String getEntityName() {
        return "City";
    }

    @Override
    public String getOrderColumn() {
        return "name";
    }
}
