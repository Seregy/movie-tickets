package ticket;

import seat.Seat;
import user.User;


import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Model that represents ticket.
 *
 * @author Seregy
 */
@Entity
public class Ticket {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    @OneToOne
    private Seat seat;

    @ManyToOne
    private User user;

    /**
     * Constructor for JPA.
     */
    protected Ticket() { }

    /**
     * Constructs new {@code Ticket} with specified seat and user.
     *
     * @param seat seat, associated with this ticket
     * @param user user, associated with this ticket
     */
    public Ticket(final Seat seat, final User user) {
        this.seat = seat;
        this.user = user;
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
     * Gets {@link user.User},
     * associated with the ticket.
     *
     * @return {@code User} object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets {@link user.User},
     * associated with the ticket.
     *
     * @param user {@code User} object
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Gets {@link seat.Seat}, associated with the ticket.
     *
     * @return {@code Seat} object
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Sets {@link seat.Seat}, associated with the ticket.
     *
     * @param seat {@code Seat} object
     */
    public void setSeat(final Seat seat) {
        this.seat = seat;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * This method accepts subclasses as parameters to work with Proxy
     * objects.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     *          argument; {@code false} otherwise.
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return Objects.equals(seat, ticket.seat)
                && Objects.equals(user, ticket.user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(seat, user);
    }

    /**
     * Returns the string representation of ticket's id, row and seat.
     *
     * @return information about the ticket
     */
    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", seat=" + seat
                + ", user=" + user
                + '}';
    }
}
