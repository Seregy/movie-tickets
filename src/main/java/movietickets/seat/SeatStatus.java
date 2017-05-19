package movietickets.seat;

import java.io.Serializable;

/**
 * Represents the status of the cinema's seat.
 *
 * @author Seregy
 */
public enum SeatStatus implements Serializable {
    /**
     * Represents a seat, available for purchase or reservation.
     */
    AVAILABLE(0),

    /**
     * Represents a seat, that was already purchased.
     */
    PURCHASED(1),

    /**
     * Represents a seat, that was reserved, but not yet purchased.
     */
    RESERVED(2);

    private int id;

    /**
     * Constructs new SeatStatus object with given id.
     *
     * @param id unique identifier
     */
    SeatStatus(final int id) {
        this.id = id;
    }

    /**
     * Gets unique identifier of the seat status.
     *
     * @return unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the item in lowercase.
     *
     * @return lowercase name
     */
    public String nameLowerCase() {
        return name().toLowerCase();
    }
}
