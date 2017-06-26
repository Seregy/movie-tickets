package movietickets.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

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
     * {@inheritDoc}
     */
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(getDateFormatter());
    }

    /**
     * Gets Date formatter.
     *
     * @return date formatter
     */
    @Bean
    public DateFormatter getDateFormatter() {
        return new DateFormatter("dd.MM.yyyy HH:mm");
    }

    /**
     * Sets view resolver for forwarding requests to specific
     * pages/views.
     *
     * @return view resolver
     */
    @Bean
    public ViewResolver getViewResolver() {
        ThymeleafViewResolver resolver =
                new ThymeleafViewResolver();
        resolver.setTemplateEngine(getSpringTemplateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    /**
     * Gets TemplateResolver for processing
     * pages with Thymeleaf tags.
     *
     * @return template resolver
     */
    @Bean
    public ITemplateResolver getTemplateResolver() {
        SpringResourceTemplateResolver resolver =
                new SpringResourceTemplateResolver();
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    /**
     * Gets SpringTemplateEngine for processing
     * pages with Thymeleaf tags.
     *
     * @return template engine
     */
    @Bean
    public SpringTemplateEngine getSpringTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(getTemplateResolver());
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }
}
