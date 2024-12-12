package com.eraboy.oop_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 *
 * This class is used to configure CORS (Cross-Origin Resource Sharing) settings for the application.
 * CORS is a security feature implemented in web browsers to prevent web pages from making requests to a different origin (domain, protocol, or port) than the one the web page was loaded from.
 * By default, Spring Boot applications do not allow CORS requests. This class enables CORS for the application.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * This method is used to add CORS mappings to the application.
     * It is called by Spring Boot when the application starts.
     *
     * @param registry The CorsRegistry instance used to add CORS mappings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Add a CORS mapping for all paths in the application
        registry.addMapping("/**")
                // Allow requests from the specified origin (in this case, http://localhost:5173)
                .allowedOrigins("http://localhost:5173")
                // Allow the specified HTTP methods (in this case, GET, POST, PUT, DELETE, and OPTIONS)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Allow all headers in the request
                .allowedHeaders("*")
                // Allow credentials (such as cookies or authentication tokens) to be sent in the request
                .allowCredentials(true);
    }
}