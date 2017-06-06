package movietickets.cinema;

import movietickets.hall.Hall;

import javax.persistence.*;
import java.util.*;

/**
 * Model that represents cinema.
 *
 * @author Seregy
 */
@Entity
public class Cinema {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;
    private String address;
    private String phone;
    private String website;
    private String pathToLogo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema")
    private Set<Hall> halls = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected Cinema() { }

    /**
     * Constructs new cinema.
     *
     * @param name full name of the cinema
     * @param address cinema's address in format [City, street, building]
     */
    public Cinema(final String name, final String address) {
        this.name = name;
        this.address = address;
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
     * Gets address of the cinema in format [City, street, building].
     *
     * @return address in format [City, street, building]
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of the cinema in format [City, street, building].
     *
     * @param address address in format [City, street, building]
     */
    public void setAddress(final String address) {
        this.address = address;
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

    /**
     * Gets cinema's phone number.
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets cinema's phone number.
     *
     * @param phone phone number
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Gets cinema's website address.
     *
     * @return website address
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets cinema's website address.
     *
     * @param website website address
     */
    public void setWebsite(final String website) {
        this.website = website;
    }

    /**
     * Gets path to cinema's logo.
     *
     * @return logo path
     */
    public String getPathToLogo() {
        return pathToLogo;
    }

    /**
     * Sets path to cinema's logo.
     *
     * @param pathToLogo path to logo
     */
    public void setPathToLogo(final String pathToLogo) {
        this.pathToLogo = pathToLogo;
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
        if (!(o instanceof Cinema)) {
            return false;
        }
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name)
                && Objects.equals(address, cinema.address);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    /**
     * Returns string representation of the cinema.
     *
     * @return cinema's information
     */
    @Override
    public String toString() {
        return "Cinema{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", address='" + address + '\''
                + ", phone='" + phone + '\''
                + ", website='" + website + '\''
                + ", pathToLogo='" + pathToLogo + '\''
                + '}';
    }
}
