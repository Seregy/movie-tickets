package movietickets.hall;

import movietickets.cinema.Cinema;
import movietickets.core.EntityWithId;
import movietickets.hall.layout.Layout;
import movietickets.session.Session;

import javax.persistence.*;
import java.util.*;
/**
 * Model that represents cinema's hall.
 *
 * @author CatReeena
 */
@Entity
public class Hall implements EntityWithId {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;
    @Embedded
    private Layout layout;

    @ManyToOne
    private Cinema cinema;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hall")
    private Set<Session> sessions = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected Hall() { }

    /**
     * Constructs new hall with specified name.
     *
     * @param name full name of the hall
     */
    public Hall(final String name) {
        this.name = name;
    }

    /**
     * Gets unique identifier of the hall.
     *
     * @return unique identifier
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Sets full name of the hall.
     *
     * @param name name of the hall
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets full name of the hall.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cinema object this hall belongs to.
     *
     * @return cinema {@link Cinema}
     */
    public Cinema getCinema() {
        return cinema;
    }

    /**
     * Sets cinema object this hall belongs to.
     *
     * @param cinema {@link Cinema} cinema this hall belongs to
     */
    public void setCinema(final Cinema cinema) {
        this.cinema = cinema;
    }

    /**
     * Gets the set of session objects that take place in the hall.
     *
     * @return sessions {@link Session}
     */
    public Set<Session> getSessions() {
        return sessions;
    }

    /**
     * Adds session object to the Set of session objects.
     * Sets session object's parameter 'hall' to 'this'.
     *
     * @param session {@link Session} session object added to the Set
     */
    public void addSession(final Session session) {
        sessions.add(session);
        session.setHall(this);
    }

    /**
     * Removes session object from the Set of session objects.
     * Sets session object's parameter 'hall' to 'null'.
     *
     * @param session {@link Session} session object removed from the Set
     */
    public void removeSession(final Session session) {
        if (sessions.remove(session)) {
            session.setHall(null);
        }
    }

    /**
     * Gets the layout object, associated with this hall.
     *
     * @return layout, associated with the hall
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Sets the layout object, associated with this hall.
     *
     * @param layout layout, associated with the hall
     */
    public void setLayout(final Layout layout) {
        this.layout = layout;
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
        if (!(o instanceof Hall)) {
            return false;
        }
        Hall hall = (Hall) o;
        return Objects.equals(name, hall.name)
                && Objects.equals(cinema, hall.cinema);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, cinema);
    }

    /**
     * Returns the string representation of hall's id and name.
     *
     * @return information about the hall
     */
    @Override
    public String toString() {
        return "Hall{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
