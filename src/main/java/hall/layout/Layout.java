package hall.layout;

import hall.Hall;

import javax.persistence.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * Model that represents hall's layout.
 *
 * @author Seregy
 */
@Entity
public class Layout {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private int rowsAmount;
    private int seatsAmount;
    @Lob
    private byte[] seats;
    @OneToOne
    private Hall hall;

    /**
     * Constructs new {@code Layout} with specified amounts of rows and seats.
     *
     * @param rowsAmount amount of rows in the hall
     * @param seatsAmount amount of seats per row
     */
    public Layout(final int rowsAmount, final int seatsAmount) {
        this.rowsAmount = rowsAmount;
        this.seatsAmount = seatsAmount;
        Seat[][] array = new Seat[rowsAmount][seatsAmount];
        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < seatsAmount; j++) {
                array[i][j] = Seat.AVAILABLE;
            }
        }
        setSeats(array);
    }

    /**
     * Constructor for JPA.
     */
    protected Layout() { }

    /**
     * Gets unique identifier of the layout.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets unique identifier of the layout.
     *
     * @param id unique identifier
     */
    public void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Gets the amount of rows in the hall.
     *
     * @return amount of rows
     */
    public int getRowsAmount() {
        return rowsAmount;
    }

    /**
     * Sets the amount of rows in the hall.
     *
     * @param rowsAmount amount of rows
     */
    public void setRowsAmount(final int rowsAmount) {
        this.rowsAmount = rowsAmount;
    }

    /**
     * Gets the amount of seats per row.
     *
     * @return amount of seats
     */
    public int getSeatsAmount() {
        return seatsAmount;
    }

    /**
     * Sets the amount of seats per row.
     *
     * @param seatsAmount amount of seats
     */
    public void setSeatsAmount(final int seatsAmount) {
        this.seatsAmount = seatsAmount;
    }

    /**
     * Gets the array of {@link Seat} objects.
     * The array is two-dimensional, first dimension is rows,
     * second - seats. Value of {@code Seat} indicate the status of
     * the seat in the hall.
     *
     * @return array of seats
     */
    public Seat[][] getSeats() {
        Seat[][] deserialized = null;
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(seats);
            ObjectInputStream in = new ObjectInputStream(byteIn)) {
            deserialized = (Seat[][]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return deserialized;
    }

    /**
     * Sets the array of {@link Seat} objects.
     * The array is two-dimensional, first dimension is rows,
     * second - seats. Value of {@code Seat} indicate the status of
     * the seat in the hall.
     *
     * @param seats array of seats
     */
    public void setSeats(final Seat[][] seats) {
        byte[] serialized = null;
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(byteOut)) {
            out.writeObject(seats);
            serialized = byteOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.seats = serialized;
    }

    /**
     * Gets the {@link hall.Hall} object,
     * associated with this layout.
     *
     * @return hall object
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Sets the {@link hall.Hall} object,
     * associated with this layout.
     *
     * @param hall hall object
     */
    public void setHall(final Hall hall) {
        this.hall = hall;
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
        if (!(o instanceof Layout)) {
            return false;
        }
        Layout layout = (Layout) o;
        return rowsAmount == layout.rowsAmount
                && seatsAmount == layout.seatsAmount
                && Arrays.equals(seats, layout.seats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(rowsAmount, seatsAmount, seats);
    }

    /**
     * Returns the string representation of layout's id, amount of rows,
     * seats per row and seats status.
     *
     * @return information about the layout
     */
    @Override
    public String toString() {
        return "Layout{"
                + "id=" + id
                + ", rowsAmount=" + rowsAmount
                + ", seatsAmount=" + seatsAmount
                + ", seats=" + Arrays.toString(getSeats())
                + '}';
    }
}
