package movietickets.user.security;

import movietickets.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;

/**
 * Permission evaluator, based on {@link CustomUserDetails}.
 * Allows access to the owner of the objects and
 * to the user with specified permission.
 * Permissions must start with the target's simple class name(e.g. User).
 *
 * @author Seregy
 */
public class UserPermissionEvaluator implements PermissionEvaluator {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final Authentication authentication,
                                 final Object targetDomainObject,
                                 final Object permission) {
        if (authentication == null || targetDomainObject == null) {
            return false;
        }

        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        boolean allowed = false;
        if (targetDomainObject instanceof User) {
            User user = (User) targetDomainObject;
            if (principal != null && user.getId() != null) {
                allowed = principal.getId().equals(user.getId());
            }
            allowed = allowed || hasPrivilege(authentication,
                            targetDomainObject.getClass().getSimpleName(),
                            permission.toString());
        }

        return allowed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final Authentication authentication,
                                 final Serializable targetId,
                                 final String targetType,
                                 final Object permission) {
        if (authentication == null || !(permission instanceof String)) {
            return false;
        }

        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        boolean allowed = false;
        if (Objects.equals(targetType, User.class.getSimpleName())) {
            if (principal != null && targetId != null) {
                allowed = Objects.equals(principal.getId(), targetId);
            }
            allowed = allowed
                    || hasPrivilege(authentication,
                    targetType,
                    permission.toString());
        }

        return allowed;
    }

    /**
     * Checks whether principal has necessary privileges.
     *
     * @param authentication represents the user in question.
     *                      Should not be null.
     * @param targetType a String representing the target's type (usually a Java
     * classname). Not null.
     * @param permission a String representation of the permission.
     *                  Must start with the target's simple class
     *                  name(e.g. User). Not null.
     * @return {@code true} if principal has privilege, {@code false} otherwise
     */
    private boolean hasPrivilege(final Authentication authentication,
                                 final String targetType,
                                 final String permission) {
        for (GrantedAuthority grantedAuthority
                : authentication.getAuthorities()) {
            if (StringUtils.startsWithIgnoreCase(
                    grantedAuthority.getAuthority(), targetType)) {
                if (StringUtils.containsIgnoreCase(
                        grantedAuthority.getAuthority(), permission)) {
                    return true;
                }
            }
        }

        return false;
    }
}
