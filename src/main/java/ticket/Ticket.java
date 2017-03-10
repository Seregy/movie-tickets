package ticket;

import java.util.Objects;
import java.util.UUID;

/**
 * Model that represents ticket.
 *
 * @author Seregy
 */
public final class Ticket {
    private UUID id;
    private UUID userId;

    private int row;
    private int seat;

    /**
     * Constructs new {@code Ticket} with specified id, user, row and seat.
     *
     * @param id unique identifier of the ticket
     * @param userId unique identifier of the {@link user.User},
     *               associated with the ticket
     * @param row row number of the ticket
     * @param seat seat number of the ticket
     */
    public Ticket(final UUID id, final UUID userId,
                  final int row, final int seat) {
        this.id = id;
        this.userId = userId;
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
     * Gets unique identifier of the {@link user.User},
     * associated with the ticket.
     *
     * @return {@code User} object
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Sets unique identifier of the {@link user.User},
     * associated with the ticket.
     *
     * @param userId {@code User} object
     */
    public void setUserId(final UUID userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
