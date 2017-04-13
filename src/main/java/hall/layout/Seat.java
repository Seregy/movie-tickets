package hall.layout;

import java.io.Serializable;

/**
 * Represents cinema's seat.
 *
 * @author Seregy
 */
public enum Seat implements Serializable {
    /**
     * Represents empty seat.
     */
    EMPTY(0),

    /**
     * Represents seat, available for purchase or reservation.
     */
    AVAILABLE(1),

    /**
     * Represents reserved seat(but not purchased).
     */
    RESERVED(2),

    /**
     * Represents seat, that has been already purchased.
     */
    UNAVAILABLE(3);

    private int id;

    /**
     * Constructs new Seat object with given id.
     *
     * @param id unique identifier
     */
    Seat(final int id) {
        this.id = id;
    }

    /**
     * Gets unique identifier of the seat.
     *
     * @return unique identifier
     */
    public int getId() {
        return id;
    }
}
