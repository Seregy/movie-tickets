package movietickets.movie;

import movietickets.session.Session;

import javax.persistence.*;
import java.util.*;

/**
 * Model that represents movie.
 *
 * @author CatReeena
 */
@Entity
public class Movie {
    @SuppressWarnings("CheckStyle")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;

    private int duration;

    private String annotation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private Set<Session> sessions = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected Movie() { }

    /**
     * Constructs new movie with specified name, duration and annotation.
     *
     * @param name full name of the movie
     * @param duration duration of the movie in minutes
     * @param annotation annotation to the movie
     */
    @SuppressWarnings("checkstyle:AvoidInlineConditionals")
    public Movie(final String name, final int duration,
                 final String annotation) {
        this.name = name;

        this.duration = duration > 0
                ? duration
                : Math.abs(duration);
        this.annotation = annotation;
    }

    /**
     * Gets unique identifier of the movie.
     *
     * @return unique identifier
     */
    public UUID getId() {
       return id;
    }

    /**
     * Sets full name of the movie.
     *
     * @param name name of the movie
     */
    public void setName(final String name) {
       this.name = name;
    }

    /**
     * Gets full name of the movie.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets duration of the movie in minutes.
     *
     * @param duration duration of the movie
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * Gets duration of the movie in minutes.
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets annotation of the movie.
     *
     * @param annotation annotation of the movie
     */
    public void setAnnotation(final String annotation) {
        this.annotation = annotation;
    }

    /**
     * Gets annotation of the movie.
     *
     * @return annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Gets the set of session objects that belong to the movie.
     *
     * @return sessions {@link Session}
     */
    public Set<Session> getSessions() {
        return sessions;
    }

    /**
     * Adds session object to the Set of session objects.
     * Sets session object's parameter 'movie' to 'this'.
     *
     * @param session {@link Session} session object added to the Set
     */
    public void addSession(final Session session) {
        sessions.add(session);
        session.setMovie(this);
    }

    /**
     * Removes session object from the Set of session objects.
     * Sets session object's parameter 'movie' to 'null'.
     *
     * @param session {@link Session} session object removed from the Set
     */
    public void removeSession(final Session session) {
        if (sessions.remove(session)) {
            session.setMovie(null);
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
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return duration == movie.duration
                && Objects.equals(name, movie.name)
                && Objects.equals(annotation, movie.annotation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, duration, annotation);
    }

    /**
     * Returns the string representation of Movie's id, name,
     * duration and annotation.
     *
     * @return information about the movie
     */
    @Override
    public String toString() {
        return "Movie{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", duration=" + duration
                + ", annotation='" + annotation + '\''
                + '}';
    }
}
