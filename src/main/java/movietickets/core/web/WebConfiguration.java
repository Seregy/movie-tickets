package movietickets.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
        return templateEngine;
    }
}
