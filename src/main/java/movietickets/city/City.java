package movietickets.city;

import movietickets.cinema.Cinema;

import javax.persistence.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Model that represents cinema.
 * Used for filtering cinemas
 * and movies.
 *
 * @author Seregy
 */
@Entity
public class City implements Serializable {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    @OrderBy("name ASC")
    private List<Cinema> cinemas = new ArrayList<>();

    /**
     * Constructor for JPA.
     */
    protected City() { }

    /**
     * Constructs new movie.
     *
     * @param name name of the city
     */
    public City(final String name) {
        this.name = name;
    }

    /**
     * Gets unique identifier of the city.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets name of the city.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the city.
     *
     * @param name name of the city
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the list of cinema objects that are located in city.
     *
     * @return cinemas {@link Cinema}
     */
    public List<Cinema> getCinemas() {
        return cinemas;
    }

    /**
     * Adds cinema object to the list of cinemas.
     * Also sets cinema's parameter 'city' to this.
     *
     * @param cinema {@link Cinema} object to be added
     */
    public void addCinema(final Cinema cinema) {
        cinemas.add(cinema);
        cinema.setCity(this);
    }

    /**
     * Removes session object from the list of sessions.
     * Also sets session object's parameter 'movie' to 'null'.
     *
     * @param cinema {@link Cinema} object to be removed
     */
    public void removeCinema(final Cinema cinema) {
        if (cinemas.remove(cinema)) {
            cinema.setCity(null);
        }
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
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns string representation of the city.
     *
     * @return city's information
     */
    @Override
    public String toString() {
        return "City{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }

    /**
     * Custom serialization method.
     * Serializes only id and name.
     *
     * @param out output stream
     * @throws IOException when serialization fails
     */
    private void writeObject(final ObjectOutputStream out)
            throws IOException {
        out.writeObject(id);
        out.writeObject(name);
    }

    /**
     * Custom deserization method.
     * Deserializes only id and name.
     *
     * @param in input stream
     * @throws IOException when deserization fails
     * @throws ClassNotFoundException when class can't be found
     */
    private void readObject(final ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        id = (UUID) in.readObject();
        name = (String) in.readObject();
    }
}
