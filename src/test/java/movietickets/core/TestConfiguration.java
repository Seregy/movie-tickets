package movietickets.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration class for Spring's IoC.
 * Contains beans' definitions that are used
 * in the tests.
 *
 * @author Seregy
 */
@Configuration
@Import(AppConfiguration.class)
public class TestConfiguration {
}
