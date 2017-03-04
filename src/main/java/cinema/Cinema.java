package main.java.cinema;

import java.util.Objects;

/**
 * Model that represents cinema.
 *
 * @author Seregy
 */
public final class Cinema {
    private int id;
    private String name;
    private String location;

    /**
     * Constructor for serialization.
     */
    private Cinema() {

    }

    /**
     * Constructs new cinema with specified id, name and location.
     *
     * @param id unique identifier of the cinema
     * @param name full name of the cinema
     * @param location location in format [City, street, building]
     */
    public Cinema(final int id, final String name, final String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    /**
     * Gets unique identifier of the cinema.
     *
     * @return unique identifier
     */
    public int getId() {
        return id;
    }


    /**
     * Sets unique identifier of the cinema.
     *
     * @param id unique identifier
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets full name of the cinema.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets full name of the cinema.
     *
     * @param name name of the cinema
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets location of the cinema in format [City, street, building].
     *
     * @return location in format [City, street, building]
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location of the cinema in format [City, street, building].
     *
     * @param location location in format [City, street, building]
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cinema cinema = (Cinema) o;
        return id == cinema.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cinema{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", location='" + location + '\''
                + '}';
    }
}
