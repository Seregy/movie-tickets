package movietickets.core;

import movietickets.cinema.dao.CinemaDAO;
import movietickets.cinema.dao.CinemaDAOHibernate;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.dao.HallDAOHibernate;
import movietickets.hall.layout.dao.LayoutDAO;
import movietickets.hall.layout.dao.LayoutDAOHibernate;
import movietickets.movie.dao.MovieDAO;
import movietickets.movie.dao.MovieDAOHibernate;
import movietickets.session.dao.SessionDAO;
import movietickets.session.dao.SessionDAOHibernate;
import movietickets.user.role.dao.RoleDAO;
import movietickets.user.role.dao.RoleDAOHibernate;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import movietickets.seat.dao.SeatDAO;
import movietickets.seat.dao.SeatDAOHibernate;
import movietickets.ticket.dao.TicketDAO;
import movietickets.ticket.dao.TicketDAOHibernate;
import movietickets.user.dao.UserDAO;
import movietickets.user.dao.UserDAOHibernate;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;


/**
 * Configuration class for Spring's IoC.
 * Contains beans' definitions that are used
 * in the application.
 *
 * @author Seregy
 */
@Configuration
@ComponentScan(basePackages = {"movietickets"})
@EnableTransactionManagement
public class AppConfiguration {
    /**
     * Gets an instance of {@link CinemaDAOHibernate} object.
     *
     * @return cinema dao object
     */
    @Primary
    @Bean
    public CinemaDAO getHibernateCinemaDAO() {
        return new CinemaDAOHibernate();
    }

    /**
     * Gets an instance of {@link HallDAOHibernate} object.
     *
     * @return hall dao object
     */
    @Primary
    @Bean
    public HallDAO getHibernateHallDAO() {
        return new HallDAOHibernate();
    }

    /**
     * Gets an instance of {@link LayoutDAOHibernate} object.
     *
     * @return layout dao object
     */
    @Primary
    @Bean
    public LayoutDAO getHibernateLayoutDAO() {
        return new LayoutDAOHibernate();
    }

    /**
     * Gets an instance of {@link movietickets.movie.Movie} object.
     *
     * @return movie dao object
     */
    @Primary
    @Bean
    public MovieDAO getHibernateMovieDAO() {
        return new MovieDAOHibernate();
    }

    /**
     * Gets an instance of {@link SeatDAOHibernate} object.
     *
     * @return layout dao object
     */
    @Primary
    @Bean
    public SeatDAO getHibernateSeatDAO() {
        return new SeatDAOHibernate();
    }

    /**
     * Gets an instance of {@link SessionDAOHibernate} object.
     *
     * @return layout dao object
     */
    @Primary
    @Bean
    public SessionDAO getHibernateSessionDAO() {
        return new SessionDAOHibernate();
    }

    /**
     * Gets an instance of {@link TicketDAOHibernate} object.
     *
     * @return ticket dao object
     */
    @Primary
    @Bean
    public TicketDAO getHibernateTicketDAO() {
        return new TicketDAOHibernate();
    }

    /**
     * Gets an instance of {@link UserDAOHibernate} object.
     *
     * @return user dao object
     */
    @Primary
    @Bean
    public UserDAO getHibernateUserDAO() {
        return new UserDAOHibernate();
    }

    /**
     * Gets an instance of {@link RoleDAOHibernate} object.
     *
     * @return role dao object
     */
    @Primary
    @Bean
    public RoleDAO getHibernateRoleDAO() {
        return new RoleDAOHibernate();
    }

    /**
     * Gets an instance of
     * {@link LocalContainerEntityManagerFactoryBean} object,
     * created either from AWS persistence
     * unit(if system property 'RDS_HOSTNAME' is set) or
     * from local persistence unit otherwise.
     *
     * @return entity manager factory
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();

        if (System.getProperty("RDS_HOSTNAME") == null) {
            entityManagerFactoryBean.setPersistenceUnitName("Local");
        } else {
            Map<String, String> properties = new HashMap<>();
            String connection = String.format("jdbc:mysql://%1$s:%2$s/%3$s",
                    System.getProperty("RDS_HOSTNAME"),
                    System.getProperty("RDS_PORT"),
                    System.getProperty("RDS_DB_NAME"));
            properties.put("javax.persistence.jdbc.user",
                    System.getProperty("RDS_USERNAME"));
            properties.put("javax.persistence.jdbc.password",
                    System.getProperty("RDS_PASSWORD"));
            properties.put("javax.persistence.jdbc.url", connection);

            entityManagerFactoryBean.setPersistenceUnitName("AWS");
            entityManagerFactoryBean.setJpaPropertyMap(properties);
        }

        return entityManagerFactoryBean;
    }

    /**
     * Gets an instance of
     * {@link PlatformTransactionManager} object,
     * created from EntityManagerFactory.
     *
     * @param entityManagerFactory entity manager factory
     * @return platform transaction manager
     */
    @Bean
    public PlatformTransactionManager getJPATransactionManager(
            final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
