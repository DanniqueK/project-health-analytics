package com.project.health_analytics.annotation;

import com.project.health_analytics.model.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify allowed user types for accessing a method or class.
 * 
 * @method value - Specifies the allowed user types.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedUserTypes {
    /**
     * Specifies the allowed user types.
     *
     * @return An array of allowed UserType values.
     */
    UserType[] value();
}