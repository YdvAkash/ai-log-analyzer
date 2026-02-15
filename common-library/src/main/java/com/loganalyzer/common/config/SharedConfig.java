package com.loganalyzer.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Shared configuration to be used across all microservices.
 * Provides a standardized ObjectMapper for JSON processing.
 */
@Configuration
public class SharedConfig {

    /**
     * Standard ObjectMapper bean that handles Java 8 Date/Time API.
     * Use @Primary to ensure this is used over default Spring beans when imported.
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Register JavaTimeModule to handle LocalDateTime serialisation
        mapper.registerModule(new JavaTimeModule());
        
        // Write dates as ISO strings (e.g., "2026-02-14T10:30:00") instead of numeric timestamps
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        // Ignore unknown properties to prevent crashes during version updates or DTO changes
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        return mapper;
    }
}