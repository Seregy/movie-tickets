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

    private String fullName;
    private String userName;
    private String password;

    @ManyToOne
    private Role role;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ticket> tickets = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected User() { }

    /**
     * Constructs new {@code User} with specified id, full name, user name,
     * password, password salt and email.
     *
     * @param fullName user's full name
     * @param userName user's short name(nickname)
     * @param password user's password
     * @param salt unique user's salt
     * @param email user's email
     */
    public User(final String fullName,
                final String userName,
                final String password,
                final Role role,
                final String email) {
        this.fullName = fullName;
        this.userName = userName;
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
     * Gets user's full name.
     *
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets user's full name.
     *
     * @param fullName full name
     */
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets user's short name(nickname).
     *
     * @return nickname
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user's short name(nickname).
     *
     * @param userName nickname
     */
    public void setUserName(final String userName) {
        this.userName = userName;
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

    public Role getRole() {
        return role;
    }

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
     * @param ticket {@link Ticket} ticket object added to the Set
     */
    public void addTicket(final Ticket ticket) {
        tickets.add(ticket);
        ticket.setUser(this);
    }

    /**
     * Removes ticket object from the Set of ticket objects.
     * Sets ticket object's parameter 'user' to 'null'.
     *
     * @param ticket {@link Ticket} ticket object removed from the Set
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
        return Objects.equals(fullName, user.fullName)
                && Objects.equals(userName, user.userName)
                && Objects.equals(password, user.password)
                && Objects.equals(role, user.role)
                && Objects.equals(email, user.email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(fullName, userName, password, role, email);
    }

    /**
     * Returns the string representation of users's id, full name,
     * user name, password, salt and email.
     *
     * @return information about the user
     */
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", fullName='" + fullName + '\''
                + ", userName='" + userName + '\''
                + ", password='" + password + '\''
                + ", role='" + role + '\''
                + ", email='" + email + '\''
                + '}';
    }
}
