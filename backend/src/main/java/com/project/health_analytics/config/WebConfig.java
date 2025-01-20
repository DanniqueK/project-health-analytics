package com.project.health_analytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.health_analytics.annotation.SessionInterceptor;

/**
 * Configuration class for setting up web-related configurations, such as
 * interceptors.
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    /**
     * Adds interceptors to the application's interceptor registry.
     * 
     * note: this config adds the sessioninterceptor to every query with the
     * specified path
     *
     * @param registry The InterceptorRegistry to which the interceptors are added.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/error",
                        "/favicon.ico",
                        "/public/**");
    }
}