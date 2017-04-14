package session;

import hall.Hall;
import movie.Movie;
import seat.Seat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Model that represents movie's session.
 *
 * @author CatReeena
 */
@Entity
public class Session {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private  UUID id;

    private LocalDateTime sessionStart;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Hall hall;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    private Set<Seat> seats = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected Session() { }

    /**
     * Constructs new session with specified date-time of the beginning.
     *
     * @param sessionStart {@link LocalDateTime} date-time of the session
     * beginning in the ISO-8601 calendar system

     */
    public Session(final LocalDateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    /**
     * Gets unique identifier of the session.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets date-time of the session beginning.
     *
     * @param sessionStart {@link LocalDateTime} date-time of the session
     * beginning in the ISO-8601 calendar system
     */
    public void setSessionStart(final LocalDateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    /**
     * Gets sessionStart of the movie.
     *
     * @return sessionStart {@link LocalDateTime}
     */
    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    /**
     * Gets the movie object this session belongs to.
     *
     * @return movie {@link Movie}
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets movie object this session belongs to.
     *
     * @param movie {@link Movie} movie this session belongs to
     */
    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

    /**
     * Gets the hall object this session belongs to.
     *
     * @return hall {@link Hall}
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Sets hall object this session belongs to.
     *
     * @param hall {@link Hall} hall this session belongs to
     */
    public void setHall(final Hall hall) {
        this.hall = hall;
    }

    /**
     * Gets the set of seat objects that belong to the session.
     *
     * @return seat {@link Seat}
     */
    public Set<Seat> getSeats() {
        return seats;
    }

    /**
     * Adds seat object to the Set of seat objects.
     * Sets seat object's parameter 'session' to 'this'.
     *
     * @param seat {@link Seat} seat object added to the Set
     */
    public void addSeat(final Seat seat) {
        seats.add(seat);
        seat.setSession(this);
    }

    /**
     * Removes seat object from the Set of seat objects.
     * Sets seat object's parameter 'session' to 'null'.
     *
     * @param seat {@link Seat} seat object removed from the Set
     */
    public void removeSeat(final Seat seat) {
        if (seats.remove(seat)) {
            seat.setSession(null);
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
        if (!(o instanceof Session)) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(sessionStart, session.sessionStart)
                && Objects.equals(movie, session.movie)
                && Objects.equals(hall, session.hall)
                && Objects.equals(seats, session.seats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(sessionStart, movie, hall, seats);
    }

    /**
     * Returns the string representation of session's id,
     * and starting time.
     *
     * @return information about the session
     */
    @Override
    public String toString() {
        return "Session{"
                + "id=" + id
                + ", sessionStart=" + sessionStart
                + '}';
    }
}
