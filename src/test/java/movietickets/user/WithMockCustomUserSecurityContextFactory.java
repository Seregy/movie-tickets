package movietickets.user;

import movietickets.user.permission.Permission;
import movietickets.user.role.Role;
import movietickets.user.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Factory for mocking Spring's security context.
 *
 * @author Seregy
 */
public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Role role = new Role(customUser.role());
        grantedAuthorities.add(
                new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
        for (String authority : customUser.authorities()) {
            role.addPermission(new Permission(authority));
            grantedAuthorities.add(
                    new SimpleGrantedAuthority(authority));
        }

        CustomUserDetails principal =
                new CustomUserDetails(UUID.fromString(customUser.id()),
                        customUser.name(),
                        customUser.password(),
                        role,
                        customUser.email(),
                        grantedAuthorities);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
