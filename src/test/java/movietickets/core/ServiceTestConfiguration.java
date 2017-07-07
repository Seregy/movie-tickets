package movietickets.core;

import movietickets.user.User;
import movietickets.user.dao.UserDAO;
import movietickets.user.dao.UserDAOHibernate;
import movietickets.user.role.Role;
import movietickets.user.role.dao.RoleDAO;
import movietickets.user.role.dao.RoleDAOHibernate;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

/**
 * Configuration class for testing
 * services.
 *
 * @author Seregy
 */
@Profile("service-test")
@Configuration
@Import(AppConfiguration.class)
public class ServiceTestConfiguration {
    @Bean
    @Primary
    public UserDAO getMockedUserDAO() {
        UserDAO dao = Mockito.mock(UserDAOHibernate.class);

        List<User> users = new ArrayList<>();

        Mockito.when(dao.find(Mockito.any())).then(invocation -> {
            UUID id = (UUID) invocation.getArguments()[0];
            return users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        });
        Mockito.when(dao.findAll()).thenReturn(users);
        Mockito.when(dao.add(Mockito.any())).then(invocation -> {
            User user = (User) invocation.getArguments()[0];
            return users.add(user);
        });
        Mockito.when(dao.update(Mockito.any())).then(invocation -> {
            User updatedUser = (User) invocation.getArguments()[0];
            User oldUser = users.stream()
                    .filter(u -> u.getId().equals(updatedUser.getId()))
                    .findAny()
                    .orElse(null);
            if (oldUser == null) {
                return false;
            }
            users.set(users.indexOf(oldUser), updatedUser);
            return true;
        });
        Mockito.when(dao.delete(Mockito.any())).then(invocation -> {
            UUID id = (UUID) invocation.getArguments()[0];
            User user = users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findAny()
                    .orElse(null);
            return user != null && users.remove(user);
        });

        return dao;
    }

    @Bean
    @Primary
    public RoleDAO getMockedRoleDAO() {
        RoleDAO dao = Mockito.mock(RoleDAOHibernate.class);

        List<Role> roles = new ArrayList<>();

        Mockito.when(dao.find(Mockito.any())).then(invocation -> {
            UUID id = (UUID) invocation.getArguments()[0];
            return roles.stream()
                    .filter(r -> r.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        });
        Mockito.when(dao.findAll()).thenReturn(roles);
        Mockito.when(dao.add(Mockito.any())).then(invocation -> {
            Role role = (Role) invocation.getArguments()[0];
            return roles.add(role);
        });
        Mockito.when(dao.update(Mockito.any())).then(invocation -> {
            Role updatedRole = (Role) invocation.getArguments()[0];
            Role oldRole = roles.stream()
                    .filter(r -> r.getId().equals(updatedRole.getId()))
                    .findAny()
                    .orElse(null);
            if (oldRole == null) {
                return false;
            }
            roles.set(roles.indexOf(oldRole), updatedRole);
            return true;
        });
        Mockito.when(dao.delete(Mockito.any())).then(invocation -> {
            UUID id = (UUID) invocation.getArguments()[0];
            Role role = roles.stream()
                    .filter(r -> r.getId().equals(id))
                    .findAny()
                    .orElse(null);
            return role != null && roles.remove(role);
        });

        return dao;
    }

    @Bean
    @Primary
    public PasswordEncoder getRawPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }
}
