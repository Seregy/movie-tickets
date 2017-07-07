package movietickets.core;

import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
     * Gets an instance of
     * {@link LocalContainerEntityManagerFactoryBean} object,
     * created either from AWS persistence
     * unit(if system property 'RDS_HOSTNAME' is set) or
     * from local persistence unit otherwise.
     *
     * @return entity manager factory
     */
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

    /**
     * Gets an instance of
     * {@link PasswordEncoder} object.
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
