package movietickets.ticket.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Repository;
import movietickets.ticket.Ticket;

/**
 * Data access object for {@link Ticket}, uses Hibernate.
 *
 * @author Seregy
 */
@Repository
public class TicketDAOHibernate
        extends AbstractDAOHibernate<Ticket> implements TicketDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Ticket> getEntityClass() {
        return Ticket.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntityName() {
        return "Ticket";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderColumn() {
        return null;
    }
}
