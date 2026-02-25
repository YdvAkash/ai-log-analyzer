package com.loganalyzer.parsing.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "processed_logs") // MongoDB Collection Name
public class LogEntity {

    @Id
    private String id; // This will be the logId from LogEvent
    
    private String source;
    private String rawContent;
    private String logLevel;    // Extracted (INFO, ERROR, etc.)
    private String message;     // Cleaned message
    
    private LocalDateTime timestamp;
    private LocalDateTime processedAt;
    
    private Map<String, Object> metadata; // IP, Hostname, etc.
    
    // AI Analysis fields (Initially null, updated by AI Service)
    private String rootCause;
    private String fixSuggestion;
}