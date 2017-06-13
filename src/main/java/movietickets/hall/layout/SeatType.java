package movietickets.hall.layout;

import java.io.Serializable;

/**
 * Represents the layout status of the cinema's seat.
 *
 * @author Seregy
 */
public enum SeatType implements Serializable {
    /**
     * Represents empty seat in the layout.
     */
    EMPTY(0),

    /**
     * Represents regular seat, available for purchase, reservation
     * and other operations.
     */
    REGULAR(1),

    /**
     * Represents seat, unavailable for purchase, reservation
     * and other operations.
     */
    UNAVAILABLE(2);

    private final int id;

    /**
     * Constructs new SeatType object with given id.
     *
     * @param id unique identifier
     */
    SeatType(final int id) {
        this.id = id;
    }

    /**
     * Gets unique identifier of the seat type.
     *
     * @return unique identifier
     */
    public int getId() {
        return id;
    }
}
