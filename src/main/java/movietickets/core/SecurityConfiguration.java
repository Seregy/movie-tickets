package movietickets.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 *
 * @author Seregy
 */
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs new security configuration.
     *
     * @param userDetailsService user detail service
     * @param passwordEncoder password encoder
     */
    @Autowired
    public SecurityConfiguration(final UserDetailsService userDetailsService,
                                 final PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configures AuthenticationManager.
     *
     * @param auth authentication manager builder
     * @throws Exception spring security exception
     */
    @Autowired
    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Configures {@link WebSecurity}.
     *
     * @param web web security
     * @throws Exception spring security exception
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    /**
     * Configures {@link HttpSecurity}.
     *
     * @param http http security
     * @throws Exception spring security exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index**").permitAll()
                .antMatchers("/user**").permitAll()
                .antMatchers("/role**").permitAll()
                .antMatchers("/cinemas**").permitAll()
                .antMatchers("/cinema/**").permitAll()
                .antMatchers("/movie/**").permitAll()
                .antMatchers("/session/**").permitAll()
                .antMatchers("/sessions/**").permitAll()
                .antMatchers("/city/**").permitAll()
                .antMatchers("/permission**").permitAll()
                .antMatchers("/admin**").permitAll()
                .antMatchers("/register**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .failureUrl("/login-error").permitAll()
                .successForwardUrl("/profile")
                .and()
                .logout().logoutSuccessUrl("/");
    }
}
