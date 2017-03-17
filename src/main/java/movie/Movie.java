package movie;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Incy on 06.03.2017.
 */
public class Movie {

private final UUID id;
private  String name;
private int duration;
private String annotation;

    public Movie(String name, int duration, String annotation)
    {
        this.name = name;
        this.duration = duration > 0 ? duration : Math.abs(duration);
        this.annotation = annotation;
        id =  UUID.randomUUID();

    }

    public Movie(final UUID id, String name, int duration, String annotation)
    {
        this.id = id;
        this.name = name;
        this.duration = duration > 0 ? duration : Math.abs(duration);
        this.annotation = annotation;

    }
    public Movie(String name)
    {
        this(name,0,"");
    }

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

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setAnnotation(String annotation)
    {
        this.annotation = annotation;
    }

    public String getAnnotation()
    {
        return annotation;
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

    @Override
    public boolean equals(Object obj) {
        if (obj==this)
            return true;
        if(obj==null||obj.getClass()!=getClass())
            return false;
        Movie movie = (Movie)obj;
        return Objects.equals(id,movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
