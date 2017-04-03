package hall;

import cinema.Cinema;
import session.Session;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Incy on 06.03.2017.
 */
@Entity
public class Hall {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    private Cinema cinema;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hall")
    private Set<Session> sessions = new HashSet<>();

    public Hall(final String name)
    {
        this.name = name;
    }

    protected Hall(){}


    public UUID getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(final Cinema cinema) {
        this.cinema = cinema;
    }

    public Set<Session> getSessions()
    {
        return sessions;
    }

    public void addSession(final Session session)
    {
        sessions.add(session);
        session.setHall(this);
    }

    public void removeSession(final Session session)
    {
        if(sessions.remove(session))
            session.setHall(null);
    }

    @Override
    public String toString()
    {
        return "hall {"
                + "id=" + id
                + ", name='" + name + '\''
                + ", cinemaId='" +cinema.getId().toString()
                + '}';
    }


}
