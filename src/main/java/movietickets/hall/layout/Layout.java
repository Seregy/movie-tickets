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
     * Constructs new {@code Layout} from given layout.
     * Elements must be string representations of SeatType values.
     * If seat's type can't be found, the type will be considered to be empty.
     *
     * @param layout new layout
     */
    public Layout(final String[][] layout) {
        this.rowsAmount = layout.length;
        this.seatsAmount = layout[0].length;
        SeatType[][] array = new SeatType[rowsAmount][seatsAmount];
        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < seatsAmount; j++) {
                SeatType type = SeatType.EMPTY;
                if (layout[i][j].equals(SeatType.REGULAR.name())) {
                    type = SeatType.REGULAR;
                } else if (layout[i][j].equals(SeatType.UNAVAILABLE.name())) {
                    type = SeatType.UNAVAILABLE;
                }
                array[i][j] = type;
            }
        }
        setSeats(trimLayoutArray(array));
    }

    /**
     * Constructor for JPA.
     */
    protected Layout() { }

    /**
     * Trims layout array by removing empty rows and columns.
     * at the beginning and at the end of the array.
     *
     * @param layout array to trim
     * @return trimmed array
     */
    private SeatType[][] trimLayoutArray(final SeatType[][] layout) {
        boolean[] rowFilled = new boolean[layout.length];
        boolean[] colFilled = new boolean[layout[0].length];


        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] != SeatType.EMPTY) {
                    rowFilled[i] = true;
                    colFilled[j] = true;
                }
            }
        }

        int rowStartIndex = -1;
        int rowEndIndex = -1;
        for (int i = 0; i < rowFilled.length; i++) {
            if (rowStartIndex == -1 && rowFilled[i]) {
                rowStartIndex = i;
            }

            if (rowEndIndex < i && rowFilled[i]) {
                rowEndIndex = i;
            }
        }

        int colStartIndex = -1;
        int colEndIndex = -1;
        for (int i = 0; i < colFilled.length; i++) {
            if (colStartIndex == -1 && colFilled[i]) {
                colStartIndex = i;
            }

            if (colEndIndex < i && colFilled[i]) {
                colEndIndex = i;
            }
        }

        SeatType[][] result = new SeatType[rowEndIndex - rowStartIndex + 1]
                        [colEndIndex - colStartIndex + 1];
        for (int resultRowIndex = 0,
             i = rowStartIndex; i < rowEndIndex + 1; i++) {
            result[resultRowIndex++] = Arrays.copyOfRange(layout[i],
                    colStartIndex,
                    colEndIndex + 1);
        }

        return result;
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
