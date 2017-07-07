package movietickets.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration class for testing
 * DAO.
 *
 * @author Seregy
 */
@Configuration
@Import(AppConfiguration.class)
public class DAOTestConfiguration {
}
