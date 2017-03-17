package session;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Incy on 06.03.2017.
 */
public class Session {

    private final UUID id;
    private LocalDateTime sessionStart;
    private UUID movieId;
    private UUID hallId;


    public Session(LocalDateTime sessionStart, UUID movieId, UUID hallId )
    {
        this.sessionStart = sessionStart;
        this.movieId = movieId;
        this.hallId = hallId;
        id = UUID.randomUUID();
    }

    public Session(UUID id, LocalDateTime sessionStart, UUID movieId, UUID hallId )
    {
        this.sessionStart = sessionStart;
        this.movieId = movieId;
        this.hallId = hallId;
        this.id = id;
    }

public UUID getId()
{return id;}

    public void setSessionStart(LocalDateTime sessionStart)
    {
        this.sessionStart = sessionStart;
    }

    public LocalDateTime getSessionStart()
    {
        return sessionStart;
    }

    public void setMovieId(UUID movieId)
    {
        this.movieId = movieId;
    }

    public UUID getMovieId()
    {
        return movieId;
    }

    public void setHallId(UUID hallId)
    {
        this.hallId = hallId;
    }

    public UUID getHallId()
    {
        return hallId;
    }

  @Override
    public String toString()
  {
      return "session{"
              + "id=" + id
              + ", sessionStart='" + sessionStart.toString() + '\''
              + ", movieId='" + movieId+ '\''
              + ", hallId='" + hallId + '\''
              + '}';
  }

    @Override
    public boolean equals(Object obj) {
        if (obj==this)
            return true;
        if(obj==null||obj.getClass()!=getClass())
            return false;
        Session session = (Session) obj;
        return Objects.equals(id,session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
