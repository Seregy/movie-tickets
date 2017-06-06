package movietickets.user.permission;

import movietickets.user.role.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Model that represents role's permission, used for security purposes.
 *
 * @author Seregy
 */
@Entity
public class Permission implements GrantedAuthority {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "permissions")
    private final Set<Role> roles = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected Permission() {

    }

    /**
     * Constructs new {@code Permission}.
     *
     * @param name string representation of the permission
     */
    public Permission(final String name) {
        this.name = name;
    }

    /**
     * Gets permission's id.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets user's role.
     *
     * @return role
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Gets string representation of the permission.
     *
     * @return representation of the permission
     */
    @Override
    public String getAuthority() {
        return name;
    }

    /**
     * Gets permission's name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets permission's name.
     *
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
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
        if (!(o instanceof Permission)) {
            return false;
        }
        Permission that = (Permission) o;
        return Objects.equals(name, that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns the string representation of permission.
     *
     * @return information about the cinema
     */
    @Override
    public String toString() {
        return "Permission{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
