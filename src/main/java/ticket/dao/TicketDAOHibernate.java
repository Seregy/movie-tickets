package ticket.dao;

import core.dao.AbstractDAOHibernate;
import org.springframework.stereotype.Service;
import ticket.Ticket;

/**
 * Data access object for {@link Ticket}, uses Hibernate.
 *
 * @author Seregy
 */
@Service
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
}
