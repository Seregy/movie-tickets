package hall.layout;

import java.io.Serializable;

/**
 * Represents the layout status of the cinema's seat.
 *
 * @author Seregy
 */
public enum SeatStatus implements Serializable {
    /**
     * Represents empty seat in the layout.
     */
    EMPTY(0),

    /**
     * Represents regular seat, available for purchase, reservation
     * and other operations.
     */
    REGULAR(1);

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
}
