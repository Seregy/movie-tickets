package session;

import hall.Hall;
import movie.Movie;
import ticket.Ticket;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Incy on 06.03.2017.
 */
@Entity
public class Session {

    @Id
    @GeneratedValue
    private  UUID id;

    private LocalDateTime sessionStart;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Hall hall;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    Set<Ticket> tickets = new HashSet<>();

    public Session(final LocalDateTime sessionStart )
    {
        this.sessionStart = sessionStart;
    }
    protected Session(){}

    public UUID getId() {
        return id;
    }

    public void setSessionStart(LocalDateTime sessionStart)
    {
        this.sessionStart = sessionStart;
    }

    public LocalDateTime getSessionStart()
    {
        return sessionStart;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(final Hall hall) {
        this.hall = hall;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(final Ticket ticket)
    {
        tickets.add(ticket);
        ticket.setSession(this);
    }

    public void removeTicket(final Ticket ticket)
    {
        if(tickets.remove(ticket))
            ticket.setSession(null);
    }

    @Override
    public String toString()
  {
      return "session{"
              + "id=" + id
              + ", sessionStart='" + sessionStart.toString() + '\''
              + ", movieId='" + movie.getId().toString()+ '\''
              + ", hallId='" + hall.getId().toString() + '\''
              + '}';
  }

}
