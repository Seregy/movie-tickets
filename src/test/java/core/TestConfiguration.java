package core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

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
    @Bean
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
