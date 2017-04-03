package cinema.dao;

import cinema.Cinema;
import core.dao.AbstractDAOHibernate;

import java.util.List;
import java.util.UUID;

/**
 * Created by Shera on 03.04.2017.
 */
public class CinemaDAOHibernate extends AbstractDAOHibernate<Cinema> implements CinemaDAO{


    public CinemaDAOHibernate()
    {
        super(PERSISTENCE_UNIT);
    }




}
