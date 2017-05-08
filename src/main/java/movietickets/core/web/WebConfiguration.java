package movietickets.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuration class for Spring's MVC.
 * Sets up general servlet configurations.
 *
 * @author Seregy
 */
@Configuration
@ComponentScan(basePackages = "movietickets")
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {
    /**
     * Enables default servlet handling.
     *
     * @param configurer default servlet configurer
     */
    @Override
    public void configureDefaultServletHandling(
            final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Adds resource handlers.
     *
     * @param registry resource registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("resources/**")
                .addResourceLocations("resources/");
    }

    /**
     * Sets view resolver for forwarding requests to specific
     * pages/views.
     *
     * @return view resolver
     */
    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
