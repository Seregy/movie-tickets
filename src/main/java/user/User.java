package user;

import ticket.Ticket;

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
public final class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String fullName;
    private String userName;
    private String password;
    private String salt;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<Ticket> tickets = new HashSet<>();

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
                final String salt,
                final String email) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
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

    /**
     * Gets user's password salt.
     *
     * @return password salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets user's password salt.
     *
     * @param salt password salt
     */
    public void setSalt(final String salt) {
        this.salt = salt;
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


    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(final Ticket ticket)
    {
        tickets.add(ticket);
        ticket.setUser(this);
    }

    public void removeTicket(final Ticket ticket)
    {
        if(tickets.remove(ticket))
            ticket.setUser(null);
    }

}
