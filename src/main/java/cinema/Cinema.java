package cinema;

import hall.Hall;

import javax.persistence.*;
import java.util.*;

/**
 * Model that represents cinema.
 *
 * @author Seregy
 */
@Entity
public final class Cinema {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;

    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema")
    private Set<Hall> halls = new HashSet<>();

    /**
     * Constructor for serialization.
     */
    protected Cinema() {

    }

    /**
     * Constructs new cinema with specified name and location.
     *
     * @param name full name of the cinema
     * @param location location in format [City, street, building]
     */
    public Cinema(final String name, final String location) {
        this.name = name;
        this.location = location;
    }

    /**
     * Gets unique identifier of the cinema.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets full name of the cinema.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets full name of the cinema.
     *
     * @param name name of the cinema
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets location of the cinema in format [City, street, building].
     *
     * @return location in format [City, street, building]
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location of the cinema in format [City, street, building].
     *
     * @param location location in format [City, street, building]
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * Gets the set of hall objects that belong to the cinema.
     *
     * @return halls {@link Hall}
     */
    public Set<Hall> getHalls() {
        return halls;
    }

    /**
     * Adds hall object to the Set of hall objects.
     * Sets hall object's parameter 'cinema' to 'this'.
     *
     * @param hall {@link Hall} hall object added to the Set
     */
    public void addHall(final Hall hall) {
        halls.add(hall);
        hall.setCinema(this);
    }


    /**
     * Removes hall object from the Set of hall objects.
     * Sets hall object's parameter 'cinema' to 'null'.
     *
     * @param hall {@link Hall} hall object removed from the Set
     */
    public void removeHall(final Hall hall) {
        if (halls.remove(hall)) {
            hall.setCinema(null);
        }
    }

    @Override
    public String toString() {
        return "Cinema{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", location='" + location + '\''
                + '}';
    }
}
