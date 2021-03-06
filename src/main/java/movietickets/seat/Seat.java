package movietickets.seat;

import movietickets.core.EntityWithId;
import movietickets.hall.layout.SeatType;
import movietickets.session.Session;
import movietickets.ticket.Ticket;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Model that represents seat, tied to specific {@link Session}.
 *
 * @author Seregy
 */
@Entity
public class Seat implements EntityWithId {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private int rowNumber;
    private int seatNumber;
    private SeatStatus seatStatus;
    private SeatType seatType;
    private int price;

    @ManyToOne
    private Session session;
    @OneToOne
    private Ticket ticket;


    /**
     * Constructs new {@code Seat} with specified row and seat number.
     *
     * @param rowNumber seat's row number
     * @param seatNumber seat's number
     * @param seatType seat's layout type
     * @param price seat's price
     */
    public Seat(final int rowNumber, final int seatNumber,
                final SeatType seatType, final int price) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        seatStatus = SeatStatus.AVAILABLE;
        this.seatType = seatType;
        this.price = price;
    }

    /**
     * Constructor for JPA.
     */
    protected Seat() { }

    /**
     * Gets unique identifier of the seat.
     *
     * @return unique identifier
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Sets unique identifier of the seat.
     *
     * @param id unique identifier
     */
    public void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Gets seat's row number.
     *
     * @return row number
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Sets seat's row number.
     *
     * @param rowNumber row number
     */
    public void setRowNumber(final int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * Gets seat's number.
     *
     * @return seat number
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets seat's number.
     *
     * @param seatNumber seat number
     */
    public void setSeatNumber(final int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Gets the session object this seat belongs to.
     *
     * @return session {@link Session}
     */
    public Session getSession() {
        return session;
    }

    /**
     * Sets session object this seat belongs to.
     *
     * @param session {@link Session} session this ticket belongs to
     */
    public void setSession(final Session session) {
        this.session = session;
    }

    /**
     * Gets {@link Ticket}, associated with this seat.
     *
     * @return ticket if the seat was bought,
     * {@code null} otherwise.
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets {@link Ticket}, associated with this seat.
     *
     * @param ticket ticket
     */
    public void setTicket(final Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Gets {@link SeatStatus} of this seat.
     *
     * @return seat status
     */
    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    /**
     * Sets {@link SeatStatus} of this seat.
     *
     * @param seatStatus status of the seat
     */
    public void setSeatStatus(final SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    /**
     * Gets {@link SeatType} of this seat.
     *
     * @return layout type of the seat
     */
    public SeatType getSeatType() {
        return seatType;
    }

    /**
     * Sets {@link SeatType} of this seat.
     *
     * @param seatType layout type of the seat
     */
    public void setSeatType(final SeatType seatType) {
        this.seatType = seatType;
    }

    /**
     * Gets price of the seat.
     *
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price of the seat.
     *
     * @param price price
     */
    public void setPrice(final int price) {
        this.price = price;
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
        if (!(o instanceof Seat)) {
            return false;
        }
        Seat seat1 = (Seat) o;
        return rowNumber == seat1.rowNumber
                && seatNumber == seat1.seatNumber
                && Objects.equals(session, seat1.session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, seatNumber, session);
    }

    /**
     * Returns the string representation of seat's id, row, seat,
     * associated with it session and ticket, and a seat status.
     *
     * @return information about the seat
     */
    @Override
    public String toString() {
        return "Seat{"
                + "id=" + id
                + ", row=" + rowNumber
                + ", seat=" + seatNumber
                + ", session=" + session
                + ", ticket=" + ticket
                + ", seatStatus=" + seatStatus
                + ", seatType=" + seatType
                + ", price=" + price
                + '}';
    }
}
