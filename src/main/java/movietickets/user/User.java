package movietickets.user;

import movietickets.ticket.Ticket;
import movietickets.user.role.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Model that represents user(both login information
 * and generic information about user).
 *
 * @author Seregy
 */
@Entity
public class User {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;
    private String password;
    private String email;

    @ManyToOne
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ticket> tickets = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected User() { }

    /**
     * Constructs new {@code User}.
     *
     * @param name user's name
     * @param password user's password
     * @param role user's role
     * @param email user's email
     */
    public User(final String name,
                final String password,
                final Role role,
                final String email) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    /**
     * Gets user's unique identifier.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets user's unique identifier.
     *
     * @param id unique identifier
     */
    public void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Gets user's short name(nickname).
     *
     * @return nickname
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user's name.
     *
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets user's password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password.
     *
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets user's role.
     *
     * @return role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets user's role.
     *
     * @param role role
     */
    public void setRole(final Role role) {
        this.role = role;
    }

    /**
     * Gets user's email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets user's email.
     *
     * @param email email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the set of ticket objects that belong to the user.
     *
     * @return tickets {@link Ticket}
     */
    public Set<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Adds ticket object to the Set of ticket objects.
     * Sets ticket object's parameter 'user' to 'this'.
     *
     * @param ticket {@link Ticket} ticket object to be added to the Set
     */
    public void addTicket(final Ticket ticket) {
        tickets.add(ticket);
        ticket.setUser(this);
    }

    /**
     * Removes ticket object from the Set of ticket objects.
     * Sets ticket object's parameter 'user' to 'null'.
     *
     * @param ticket {@link Ticket} ticket object to be removed from the Set
     */
    public void removeTicket(final Ticket ticket) {
        if (tickets.remove(ticket)) {
            ticket.setUser(null);
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
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(password, user.password)
                && Objects.equals(role, user.role)
                && Objects.equals(email, user.email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, password, role, email);
    }

    /**
     * Returns the string representation the user.
     *
     * @return information about the user
     */
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", password='" + password + '\''
                + ", role='" + role + '\''
                + ", email='" + email + '\''
                + '}';
    }
}
