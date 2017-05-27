package movietickets.user;

import movietickets.ticket.Ticket;
import movietickets.user.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.Collection;

/**
 * Model that represents information about {@link User},
 * retrieved by a {@link UserDetailsService}.
 *
 * @author Seregy
 */
public class CustomUserDetails
        extends org.springframework.security.core.userdetails.User {
    private final Role role;
    private final String email;
    private final Collection<Ticket> tickets;

    /**
     * Constructs new CustomUserDetails.
     *
     * @param username user's name
     * @param password user's password
     * @param role user's role
     * @param email user's email
     * @param tickets user's tickets
     * @param authorities user's authorities
     */
    public CustomUserDetails(final String username, final String password,
                             final Role role,
                             final String email,
                             final Collection<Ticket> tickets,
                             final Collection<? extends GrantedAuthority>
                                     authorities) {
        super(username, password, authorities);
        this.role = role;
        this.email = email;
        this.tickets = tickets;
    }

    /**
     * Gets user's role.
     *
     * @return user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Get's user's email.
     *
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get's user's tickets.
     *
     * @return user's tickets
     */
    public Collection<Ticket> getTickets() {
        return tickets;
    }
}
