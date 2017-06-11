package movietickets.ticket.dao;

import movietickets.core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Service;
import movietickets.ticket.Ticket;

import javax.transaction.Transactional;

/**
 * Data access object for {@link Ticket}, uses Hibernate.
 *
 * @author Seregy
 */
@Service
@Transactional
public final class TicketDAOHibernate
        extends AbstractDAOHibernate<Ticket> implements TicketDAO {
    @Override
    public Class<Ticket> getEntityClass() {
        return Ticket.class;
    }

    @Override
    public String getEntityName() {
        return "Ticket";
    }

    @Override
    public String getOrderColumn() {
        return null;
    }
}
