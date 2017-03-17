package hall;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Incy on 06.03.2017.
 */
public class Hall {

    private final UUID id;
    private String name;
    private  UUID cinemaId;

    public Hall(String name, final UUID cinema)
    {
        this.name = name;
        this.cinemaId = cinema;
        id = UUID.randomUUID();
    }

    public Hall(UUID id,String name, final UUID cinema)
    {
        this.id = id;
        this.name = name;
        this.cinemaId = cinema;
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

    public UUID getCinemaId()
    {
        return cinemaId;
    }

    @Override
    public String toString()
    {
        return "hall {"
                + "id=" + id
                + ", name='" + name + '\''
                + ", cinemaId='" +cinemaId.toString()
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this)
            return true;
        if(obj==null||obj.getClass()!=getClass())
            return false;
        Hall hall = (Hall)obj;
        return Objects.equals(id,hall.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
