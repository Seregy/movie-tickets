package movietickets.user.role;

import movietickets.user.User;
import movietickets.user.permission.Permission;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Model that represents user's role, used for security purposes.
 *
 * @author Seregy
 */
@Entity
public class Role implements GrantedAuthority {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "role")
    private final Set<User> users = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private final Set<Permission> permissions = new HashSet<>();

    /**
     * Constructor for JPA.
     */
    protected Role() {

    }

    /**
     * Constructs new {@code Role}.
     *
     * @param name string representation of the role
     */
    public Role(final String name) {
        this.name = name;
    }

    /**
     * Gets role's id.
     *
     * @return unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets role's name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets role's name.
     *
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets all users, associated with this role.
     *
     * @return users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Adds user to this role.
     *
     * @param user user to be added
     */
    public void addUser(final User user) {
        users.add(user);
        user.setRole(this);
    }

    /**
     * Removes user from this role.
     *
     * @param user user to be removed
     */
    public void removeUser(final User user) {
        users.remove(user);
        user.setRole(null);
    }

    /**
     * Gets permissions, associated with this role.
     *
     * @return permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Adds permission to this role.
     *
     * @param permission permission to be added
     */
    public void addPermission(final Permission permission) {
        permissions.add(permission);
        permission.getRoles().add(this);
    }

    /**
     * Removes permission from this role.
     *
     * @param permission to be removed
     */
    public void removePermission(final Permission permission) {
        permissions.remove(permission);
        permission.getRoles().remove(this);
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
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns the string representation the user.
     *
     * @return information about role
     */
    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }

    /**
     * Gets string representation of the role.
     *
     * @return representation of the role
     */
    @Override
    public String getAuthority() {
        return name;
    }
}
