package movietickets.hall.layout;

import javax.persistence.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Model that represents hall's layout.
 *
 * @author Seregy
 */
@Embeddable
public class Layout {
    private int rowsAmount;
    private int seatsAmount;
    @Lob
    private byte[] seatTypes;

    /**
     * Constructs new {@code Layout} with specified amounts of rows and seats
     * per row.
     *
     * @param rowsAmount amount of rows in the hall
     * @param seatsAmount amount of seats per row
     */
    public Layout(final int rowsAmount, final int seatsAmount) {
        this.rowsAmount = rowsAmount;
        this.seatsAmount = seatsAmount;
        SeatType[][] array = new SeatType[rowsAmount][seatsAmount];
        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < seatsAmount; j++) {
                array[i][j] = SeatType.REGULAR;
            }
        }
        setSeats(array);
    }

    /**
     * Constructor for JPA.
     */
    protected Layout() { }

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
     * Gets the array of {@link SeatType} objects.
     * The array is two-dimensional, first dimension is rows,
     * second - seats. Value of {@code SeatType} indicate the type of
     * the seat in the hall.
     *
     * @return array of seats types
     */
    public SeatType[][] getSeatsTypes() {
        SeatType[][] deserialized = null;
        try (ByteArrayInputStream byteIn =
                     new ByteArrayInputStream(seatTypes);
             ObjectInputStream in = new ObjectInputStream(byteIn)) {
            deserialized = (SeatType[][]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return deserialized;
    }

    /**
     * Sets the array of {@link SeatType} objects.
     * The array is two-dimensional, first dimension is rows,
     * second - seatTypes. Value of {@code SeatType} indicate the type of
     * the seat in the hall.
     *
     * @param newSeatTypes array of seats types
     */
    public void setSeats(final SeatType[][] newSeatTypes) {
        byte[] serialized = null;
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(byteOut)) {
            out.writeObject(newSeatTypes);
            serialized = byteOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.seatTypes = serialized;
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
                && Arrays.equals(seatTypes, layout.seatTypes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(rowsAmount, seatsAmount, seatTypes);
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
                + ", rowsAmount=" + rowsAmount
                + ", seatsAmount=" + seatsAmount
                + ", seats statuses=" + Arrays.toString(getSeatsTypes())
                + '}';
    }
}
