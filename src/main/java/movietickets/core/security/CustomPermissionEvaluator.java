package movietickets.core.security;

import movietickets.core.EntityWithId;
import movietickets.ticket.Ticket;
import movietickets.ticket.service.TicketService;
import movietickets.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Permission evaluator, based on {@link CustomUserDetails}.
 *
 * Allows access to the owner of the object(if target is
 * an instance of {@link User} or {@link Ticket}) and
 * to the user with necessary permission.
 * Permissions must start with the target's simple class name(e.g. User) and
 * contain either a String representation of the target's id(e.g.
 * 'cinema_edit_00000000-0000-0000-0000-000000000000') or 'all' keyword(e.g.
 * 'cinema_edit_all').
 *
 * @author Seregy
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final String ALLOW_ACCESS_TO_ALL_ENTITIES_KEYWORD = "all";

    private TicketService ticketService;

    /**
     * Constructs new permission evaluator.
     *
     * @param ticketService ticket service
     */
    public CustomPermissionEvaluator(final TicketService ticketService) {
        this.ticketService = ticketService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final Authentication authentication,
                                 final Object targetDomainObject,
                                 final Object permission) {
        if (targetDomainObject == null) {
            return false;
        }

        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        boolean allowed = false;
        if (targetDomainObject instanceof User) {
            User user = (User) targetDomainObject;
            if (user.getId() != null) {
                allowed = principal.getId().equals(user.getId());
            }
        } else if (targetDomainObject instanceof Ticket) {
            Ticket ticket = (Ticket) targetDomainObject;
            if (ticket.getId() != null) {
                allowed = principal.getId().equals(ticket.getUser().getId());
            }
        }

        String id = null;
        if (targetDomainObject instanceof EntityWithId) {
            id = ((EntityWithId) targetDomainObject).getId().toString();
        }

        return allowed || hasPrivilege(authentication,
                targetDomainObject.getClass().getSimpleName(),
                permission.toString(),
                id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final Authentication authentication,
                                 final Serializable targetId,
                                 final String targetType,
                                 final Object permission) {
        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        boolean allowed = false;
        String id = null;
        if (targetId != null) {
            if (Objects.equals(targetType, User.class.getSimpleName())) {
                allowed = Objects.equals(principal.getId(), targetId);
            } else if (Objects.equals(targetType,
                    Ticket.class.getSimpleName())) {
                UUID ticketId = UUID.fromString(targetId.toString());
                Ticket ticket = ticketService.get(ticketId);
                allowed = Objects.equals(principal.getId(),
                        ticket.getUser().getId());
            }

            id = targetId.toString();
        }

        return allowed || hasPrivilege(authentication,
                targetType,
                permission.toString(),
                id);
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
     * @param targetId a String representation of the identifier
     *                for the object instance. Can be null.
     * @return {@code true} if principal has privilege, {@code false} otherwise
     */
    private boolean hasPrivilege(final Authentication authentication,
                                 final String targetType,
                                 final String permission,
                                 final @Nullable String targetId) {
        for (GrantedAuthority grantedAuthority
                : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            if (StringUtils.startsWithIgnoreCase(authority, targetType)) {
                if (StringUtils.containsIgnoreCase(authority, permission)) {
                    if (targetId != null) {
                        if (StringUtils.containsIgnoreCase(authority,
                                targetId)) {
                            return true;
                        }
                    }

                    if (StringUtils.containsIgnoreCase(authority,
                            ALLOW_ACCESS_TO_ALL_ENTITIES_KEYWORD)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
