package movietickets.core;

import movietickets.core.dao.DAO;
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
        return getMockedDAO(UserDAOHibernate.class);
    }

    @Bean
    @Primary
    public RoleDAO getMockedRoleDAO() {
        return getMockedDAO(RoleDAOHibernate.class);
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

    private <E extends EntityWithId, T extends DAO<E>> T getMockedDAO(Class<? extends T> daoClass) {
        T mockedDAO = Mockito.mock(daoClass);
        List<E> entities = new ArrayList<>();

        Mockito.when(mockedDAO.find(Mockito.any())).then(invocation -> {
            UUID id = (UUID) invocation.getArguments()[0];
            return entities.stream()
                    .filter(e -> e.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        });
        Mockito.when(mockedDAO.findAll()).thenReturn(entities);
        Mockito.when(mockedDAO.add(Mockito.any())).then(invocation -> {
            @SuppressWarnings("unchecked")
            E entity = (E) invocation.getArguments()[0];
            return entities.add(entity);
        });
        Mockito.when(mockedDAO.update(Mockito.any())).then(invocation -> {
            @SuppressWarnings("unchecked")
            E updatedEntity = (E) invocation.getArguments()[0];
            E oldEntity = entities.stream()
                    .filter(e -> e.getId().equals(updatedEntity.getId()))
                    .findAny()
                    .orElse(null);
            if (oldEntity == null) {
                return false;
            }
            entities.set(entities.indexOf(oldEntity), updatedEntity);
            return true;
        });
        Mockito.when(mockedDAO.delete(Mockito.any())).then(invocation -> {
            UUID id = (UUID) invocation.getArguments()[0];
            E entity = entities.stream()
                    .filter(e -> e.getId().equals(id))
                    .findAny()
                    .orElse(null);
            return entity != null && entities.remove(entity);
        });

        return mockedDAO;
    }
}
