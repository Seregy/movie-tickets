package movietickets.core;

import movietickets.ticket.service.TicketService;
import movietickets.core.security.CustomPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TicketService ticketService;

    /**
     * Constructs new method security configuration.
     *
     * @param ticketService ticket service
     */
    @Autowired
    public MethodSecurityConfiguration(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(
                new CustomPermissionEvaluator(ticketService));
        return expressionHandler;
    }
}
