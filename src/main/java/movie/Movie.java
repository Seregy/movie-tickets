package movie;

import session.Session;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Incy on 06.03.2017.
 */
@Entity
public class Movie {

    @Id
    @GeneratedValue
    @Column( columnDefinition = "BINARY(16)", length = 16 )
    private  UUID id;

    private  String name;

    private int duration;

    private String annotation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    Set<Session> sessions = new HashSet<>();

    public Movie(final String name, final int duration,final String annotation)
    {
        this.name = name;
        this.duration = duration > 0 ? duration : Math.abs(duration);
        this.annotation = annotation;
    }

    protected Movie(){}

    public Movie(final String name)
    {
        this(name,0,"");
    }

    public UUID getId()
    {
       return id;
    }

    public void setName(final String name)
    {
       this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setDuration(final int duration)
    {
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setAnnotation(final String annotation)
    {
        this.annotation = annotation;
    }

    public String getAnnotation()
    {
        return annotation;
    }

    public Set<Session> getSessions()
    {
        return sessions;
    }

    public void addSession(final Session session)
    {
        sessions.add(session);
        session.setMovie(this);
    }

    public void removeSession(final Session session)
    {
        if(sessions.remove(session))
            session.setMovie(null);
    }

    @Override
    public String toString()
    {
        return "movie{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", duration='" + duration + " minutes"+ '\''
            + ", annotation='" + annotation + '\''
            + '}';
    }

}
