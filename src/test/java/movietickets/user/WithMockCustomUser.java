package movietickets.user;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for specifying custom user details
 * for mocking Spring's security context.
 *
 * @author Seregy
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String id() default "00000000-0000-0000-0000-000000000000";
    String name() default "user";
    String email() default "mail@mail.com";
    String password() default "password";
    String role() default "User";
    String[] authorities() default {};
}
