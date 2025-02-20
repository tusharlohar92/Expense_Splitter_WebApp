package config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the ModelMapper bean for DTO-entity conversions.
 * Uses strict mapping strategy to prevent silent mapping errors.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() { // Removed public modifier per Spring best practices
        ModelMapper modelMapper = new ModelMapper();
        
        // Configure mapping rules
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT) // Exact field name matching
            .setFieldMatchingEnabled(true) // Allow direct field access (bypass getters/setters)
            .setSkipNullEnabled(true) // Skip null values during mapping
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // Access private fields
        
        return modelMapper;
    }
}
