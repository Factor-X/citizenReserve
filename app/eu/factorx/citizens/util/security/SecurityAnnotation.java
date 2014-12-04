package eu.factorx.citizens.util.security;

import play.mvc.With;

import java.lang.annotation.*;

/**
 * Created by florian on 4/12/14.
 */
@With(SecurityAnnotationAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface SecurityAnnotation {
    boolean isSuperAdmin();
}
