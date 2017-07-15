package movietickets.core;

import movietickets.cinema.dao.CinemaDAO;
import movietickets.cinema.dao.CinemaDAOHibernate;
import movietickets.city.dao.CityDAO;
import movietickets.city.dao.CityDAOHibernate;
import movietickets.core.dao.DAO;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.dao.HallDAOHibernate;
import movietickets.movie.dao.MovieDAO;
import movietickets.movie.dao.MovieDAOHibernate;
import movietickets.seat.dao.SeatDAO;
import movietickets.seat.dao.SeatDAOHibernate;
import movietickets.session.dao.SessionDAO;
import movietickets.session.dao.SessionDAOHibernate;
import movietickets.ticket.dao.TicketDAO;
import movietickets.ticket.dao.TicketDAOHibernate;
import movietickets.user.dao.UserDAO;
import movietickets.user.dao.UserDAOHibernate;
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
    public TicketDAO getMockedTicketDAO() {
        return getMockedDAO(TicketDAOHibernate.class);
    }

    @Bean
    @Primary
    public SeatDAO getMockedSeatDAO() {
        return getMockedDAO(SeatDAOHibernate.class);
    }

    @Bean
    @Primary
    public CityDAO getMockedCityDAO() {
        return getMockedDAO(CityDAOHibernate.class);
    }

    @Bean
    @Primary
    public CinemaDAO getMockedCinemaDAO() {
        return getMockedDAO(CinemaDAOHibernate.class);
    }

    @Bean
    @Primary
    public HallDAO getMockedHallDAO() {
        return getMockedDAO(HallDAOHibernate.class);
    }

    @Bean
    @Primary
    public MovieDAO getMockedMovieDAO() {
        return getMockedDAO(MovieDAOHibernate.class);
    }

    @Bean
    @Primary
    public SessionDAO getMockedSessionDAO() {
        return getMockedDAO(SessionDAOHibernate.class);
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
