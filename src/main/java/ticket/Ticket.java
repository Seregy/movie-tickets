package ticket;

import session.Session;
import user.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

/**
 * Model that represents ticket.
 *
 * @author Seregy
 */
@Entity
public final class Ticket {
    @Id
    @GeneratedValue
    private UUID id;

    private int row;
    private int seat;

    @ManyToOne
    private Session session;

    @ManyToOne
    private User user;

    /**
     * Constructs new {@code Ticket} with specified id, user, row and seat.
     *
     * @param user user {@link user.User},
     *               associated with the ticket
      @param session session {@link session.Session},
     *               associated with the ticket
     * @param row row number of the ticket
     * @param seat seat number of the ticket
     */
    public Ticket(final User user, final Session session,
                  final int row, final int seat) {
        this.session = session;
        this.user = user;
        this.row = row;
        this.seat = seat;
    }

    /**
     * Gets unique identifier of the ticket.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets unique identifier of the ticket.
     *
     * @param id unique identifier
     */
    public void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Gets user {@link user.User},
     * associated with the ticket.
     *
     * @return {@code User} object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user {@link user.User},
     * associated with the ticket.
     *
     * @param user {@code User} object
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Gets ticket's row number.
     *
     * @return row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets ticket's row number.
     *
     * @param row row number
     */
    public void setRow(final int row) {
        this.row = row;
    }

    /**
     * Gets ticket's seat number.
     *
     * @return seat number
     */
    public int getSeat() {
        return seat;
    }

    /**
     * Sets ticket's seat number.
     *
     * @param seat seat number
     */
    public void setSeat(final int seat) {
        this.seat = seat;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(final Session session) {
        this.session = session;
    }
}
