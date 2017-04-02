/*
package core.web;

import cinema.dao.CinemaDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ticket.dao.TicketDAO;
import user.dao.UserDAO;

*/
/**
 * Configuration class for Spring's IoC.
 * Contains beans' definitions that are used
 * in the web module of the application.
 *
 * @author Seregy
 *//*



@Configuration
@ComponentScan(basePackages = {"cinema.dao", "ticket.dao", "user.dao"})
public class WebConfiguration {
    */
/**
     * Gets an instance of {@link CinemaDAODefault} object.
     *
     * @return dao object
     *//*

    @Primary
    @Bean
    public CinemaDAO getMySQLCinemaDAO() {
        return new CinemaDAODefault();
    }

    */
/**
     * Gets an instance of {@link CinemaDAOMongo} object.
     * It's a primary source of CinemaDAO.
     *
     * @return dao object
     *//*

    @Bean
    public CinemaDAO getMongoCinemaDAO() {
        return new CinemaDAOMongo();
    }

    */
/**
     * Gets an instance of {@link TicketDAODefault} object.
     *
     * @return dao object
     *//*

    @Primary
    @Bean
    public TicketDAO getMySQLTicketDAO() {
        return new TicketDAODefault();
    }

    */
/**
     * Gets an instance of {@link TicketDAOMongo} object.
     * It's a primary source of TicketDAO.
     *
     * @return dao object
     *//*

    @Bean
    public TicketDAO getMongoTicketDAO() {
        return new TicketDAOMongo();
    }


    */
/**
     * Gets an instance of {@link UserDAODefault} object.
     * It's a primary source of UserDAO.
     *
     * @return dao object
     *//*

    @Primary
    @Bean
    public UserDAO getMySQLUserDAO() {
        return new UserDAODefault();
    }

    */
/**
     * Gets an instance of {@link UserDAOMongo} object.
     *
     * @return dao object
     *//*

    @Bean
    public UserDAO getMongoDAO() {
        return new UserDAOMongo();
    }
}
*/
