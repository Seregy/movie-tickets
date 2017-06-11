package movietickets.movie;

import movietickets.session.Session;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Lob
    private String annotation;
    private int year;
    private String country;
    private String genres;
    private String cast;
    private String director;
    private LocalDate screeningDate;
    private LocalDate premiereEndDate;
    private int duration;
    private String contentRating;
    private String pathToPoster;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    @OrderBy("sessionStart ASC")
    private List<Session> sessions = new ArrayList<>();

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
    public Movie(final String name, final int duration,
                 final String annotation) {
        this.name = name;
        this.duration = duration;
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
     * Gets movie's release year.
     *
     * @return release year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets movie's release year.
     *
     * @param year release year
     */
    public void setYear(final int year) {
        this.year = year;
    }

    /**
     * Gets movie's country of creation.
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets movie's country of creation.
     *
     * @param country country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * Gets movie's genres.
     *
     * @return genres
     */
    public String getGenres() {
        return genres;
    }

    /**
     * Sets movie's genres.
     *
     * @param genres genres
     */
    public void setGenres(final String genres) {
        this.genres = genres;
    }

    /**
     * Gets movie's cast.
     *
     * @return cast
     */
    public String getCast() {
        return cast;
    }

    /**
     * Sets movie's cast.
     *
     * @param cast cast
     */
    public void setCast(final String cast) {
        this.cast = cast;
    }

    /**
     * Gets movie's director.
     *
     * @return director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets movie's director.
     *
     * @param director director
     */
    public void setDirector(final String director) {
        this.director = director;
    }

    /**
     * Gets movie's screening date.
     *
     * @return screening date
     */
    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    /**
     * Sets movie's screening date.
     *
     * @param screeningDate screening date
     */
    public void setScreeningDate(final LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    /**
     * Gets end date of the movie's premiere period.
     *
     * @return premiere end date
     */
    public LocalDate getPremiereEndDate() {
        return premiereEndDate;
    }

    /**
     * Sets end date of the movie's premiere period.
     *
     * @param premiereEndDate premiere end date
     */
    public void setPremiereEndDate(final LocalDate premiereEndDate) {
        this.premiereEndDate = premiereEndDate;
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
     * Gets content rating of the movie.
     *
     * @return content rating
     */
    public String getContentRating() {
        return contentRating;
    }

    /**
     * Sets content rating of the movie.
     *
     * @param contentRating content rating
     */
    public void setContentRating(final String contentRating) {
        this.contentRating = contentRating;
    }

    /**
     * Gets path to movie's poster.
     *
     * @return path to poster
     */
    public String getPathToPoster() {
        return pathToPoster;
    }

    /**
     * Sets path to movie's poster.
     *
     * @param pathToPoster path to poster.
     */
    public void setPathToPoster(final String pathToPoster) {
        this.pathToPoster = pathToPoster;
    }

    /**
     * Gets the list of session objects that belong to the movie.
     *
     * @return sessions {@link Session}
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Adds session object to the set of session objects.
     * Also sets session's parameter 'movie' to this.
     *
     * @param session {@link Session} object to be added
     */
    public void addSession(final Session session) {
        sessions.add(session);
        session.setMovie(this);
    }

    /**
     * Removes session object from the set of session objects.
     * Also sets session's parameter 'movie' to 'null'.
     *
     * @param session {@link Session} object to be removed
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
        return Objects.hash(name, annotation, duration);
    }

    /**
     * Returns string representation of the movie.
     *
     * @return movie's information
     */
    @Override
    public String toString() {
        return "Movie{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", annotation='" + annotation + '\''
                + ", year=" + year
                + ", country='" + country + '\''
                + ", genres='" + genres + '\''
                + ", cast='" + cast + '\''
                + ", director='" + director + '\''
                + ", screeningDate=" + screeningDate
                + ", premiereEndDate=" + premiereEndDate
                + ", duration=" + duration
                + ", contentRating='" + contentRating + '\''
                + ", pathToPoster='" + pathToPoster + '\''
                + '}';
    }
}
