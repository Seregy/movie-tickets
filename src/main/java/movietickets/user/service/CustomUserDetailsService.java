package movietickets.user.service;

import movietickets.user.CustomUserDetails;
import movietickets.user.User;
import movietickets.user.permission.Permission;
import movietickets.user.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom user details service, works with {@link CustomUserDetails}.
 *
 * @author Seregy
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Constructs new CustomUserDetailsService.
     *
     * @param userService user service object
     */
    @Autowired
    public CustomUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads user by its name.
     *
     * @param username user's name
     * @return fully populated user record
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Transactional
    @Override
    public CustomUserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        User user = userService.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Couldn't find user with name '%s'",
                            username));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Role role = user.getRole();
        grantedAuthorities.add(
                new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
        for (Permission permission : role.getPermissions()) {
            grantedAuthorities.add(
                    new SimpleGrantedAuthority(permission.getAuthority()));
        }

        return new CustomUserDetails(user.getName(), user.getPassword(),
                user.getRole(), user.getEmail(),
                user.getTickets(), grantedAuthorities);
    }
}
