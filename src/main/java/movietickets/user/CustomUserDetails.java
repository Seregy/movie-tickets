package movietickets.user;

import movietickets.user.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.Collection;
import java.util.UUID;

/**
 * Model that represents information about {@link User},
 * retrieved by a {@link UserDetailsService}.
 *
 * @author Seregy
 */
public class CustomUserDetails
        extends org.springframework.security.core.userdetails.User {
    private final UUID id;
    private final Role role;
    private String email;

    /**
     * Constructs new CustomUserDetails.
     *
     * @param id user's id
     * @param username user's name
     * @param password user's password
     * @param role user's role
     * @param email user's email
     * @param authorities user's authorities
     */
    public CustomUserDetails(final UUID id,
                             final String username,
                             final String password,
                             final Role role,
                             final String email,
                             final Collection<? extends GrantedAuthority>
                                     authorities) {
        super(username, password, authorities);
        this.id = id;
        this.role = role;
        this.email = email;
    }

    /**
     * Gets user's identifier.
     *
     * @return user's id
     */
    public UUID getId() {
        return id;
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
     * Sets user's email.
     *
     * @param email user's email
     */
    public void setEmail(final String email) {
        this.email = email;
    }
}
