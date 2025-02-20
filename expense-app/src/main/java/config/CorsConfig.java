package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) settings for the application.
 * Allows cross-origin requests from specified origins with defined methods and headers.
 */
@Configuration
public class CorsConfig {

    @Bean
    WebMvcConfigurer corsConfigurer() { // Removed public modifier for Spring's recommendation
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints
                    .allowedOrigins("*") // ⚠️ In production, replace with specific domains
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                    .allowedHeaders("*") // Allowed request headers
                    .exposedHeaders("Authorization", "Content-Type") // Headers exposed to clients
                    .allowCredentials(false) // Disable credentials for wildcard origins
                    .maxAge(3600); // Cache preflight responses for 1 hour
            }
        };
    }
}
