package movietickets.core;

import movietickets.user.security.UserPermissionEvaluator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Configuration class for Spring Method Security.
 *
 * @author Seregy
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration
        extends GlobalMethodSecurityConfiguration {
    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new UserPermissionEvaluator());
        return expressionHandler;
    }
}
