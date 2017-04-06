package session;

import hall.Hall;
import movie.Movie;
import ticket.Ticket;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private Set<Ticket> tickets = new HashSet<>();

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
     * Gets the set of ticket objects that belong to the session.
     *
     * @return tickets {@link Ticket}
     */
    public Set<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Adds ticket object to the Set of ticket objects.
     * Sets ticket object's parameter 'session' to 'this'.
     *
     * @param ticket {@link Ticket} ticket object added to the Set
     */
    public void addTicket(final Ticket ticket) {
        tickets.add(ticket);
        ticket.setSession(this);
    }

    /**
     * Removes ticket object from the Set of ticket objects.
     * Sets ticket object's parameter 'session' to 'null'.
     *
     * @param ticket {@link Ticket} ticket object removed from the Set
     */
    public void removeTicket(final Ticket ticket) {
        if (tickets.remove(ticket)) {
            ticket.setSession(null);
        }
    }

    /**
     * Returns the string representation of session's id,
     * starting time, movie's id and hall's id.
     *
     * @return information about the session
     */
    @Override
    public String toString() {
      return "session{"
              + "id=" + id
              + ", sessionStart='" + sessionStart.toString() + '\''
              + ", movieId='" + movie.getId().toString() + '\''
              + ", hallId='" + hall.getId().toString() + '\''
              + '}';
  }

}
