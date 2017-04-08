package core.web;

import cinema.dao.CinemaDAO;
import cinema.dao.CinemaDAOHibernate;
import org.springframework.context.annotation.*;
import ticket.dao.TicketDAO;
import ticket.dao.TicketDAOHibernate;
import user.dao.UserDAO;
import user.dao.UserDAOHibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;


/**
 * Configuration class for Spring's IoC.
 * Contains beans' definitions that are used
 * in the web module of the application.
 *
 * @author Seregy
 */
@Configuration
@ComponentScan
public class WebConfiguration {
    /**
     * Gets an instance of {@link CinemaDAOHibernate} object.
     *
     * @return cinema dao object
     */
    @Primary
    @Bean
    public CinemaDAO getMySQLCinemaDAO() {
        return new CinemaDAOHibernate();
    }

    /**
     * Gets an instance of {@link TicketDAOHibernate} object.
     *
     * @return ticket dao object
     */
    @Primary
    @Bean
    public TicketDAO getMySQLTicketDAO() {
        return new TicketDAOHibernate();
    }

    /**
     * Gets an instance of {@link user.dao.UserDAOHibernate} object.
     *
     * @return user dao object
     */
    @Primary
    @Bean
    public UserDAO getMySQLUserDAO() {
        return new UserDAOHibernate();
    }

    /**
     * Gets an instance of {@link EntityManagerFactory} object,
     * created from local persistence unit.
     *
     * @return entity manager factory
     */
    @Bean
    public EntityManagerFactory getLocalEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("Local");
    }

    /**
     * Gets an instance of {@link EntityManagerFactory} object,
     * created either from AWS persistence
     * unit(if system property 'RDS_HOSTNAME' is set) or
     * from local persistence unit otherwise.
     *
     * @return entity manager factory
     */
    @Primary
    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        if (System.getProperty("RDS_HOSTNAME") == null) {
            return getLocalEntityManagerFactory();
        }
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
        return Persistence.createEntityManagerFactory("AWS", properties);
    }
}
