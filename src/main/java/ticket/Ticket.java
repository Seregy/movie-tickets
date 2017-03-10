package ticket;

import user.User;

import java.util.UUID;

/**
 * Model that represents ticket.
 *
 * @author Seregy
 */
public class Ticket {
    private UUID id;
    private User user;

    private int row;
    private int seat;

    /**
     * Constructs new {@code Ticket} with specified id, user, row and seat.
     *
     * @param id unique identifier of the ticket
     * @param user {@link User} associated with the ticket
     * @param row row number of the ticket
     * @param seat seat number of the ticket
     */
    public Ticket(final UUID id, final User user,
                  final int row, final int seat) {
        this.id = id;
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
     * Gets {@link User}, associated with the ticket.
     *
     * @return {@code User} object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets {@link User}, associated with the ticket.
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
}
