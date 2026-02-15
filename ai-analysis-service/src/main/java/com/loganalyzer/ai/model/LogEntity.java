package com.loganalyzer.ai.model;

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
@Document(collection = "processed_logs") // Same collection as Parsing Service
public class LogEntity {

    @Id
    private String id; 
    
    private String source;
    private String rawContent;
    private String logLevel;
    private String message;
    
    private LocalDateTime timestamp;
    private LocalDateTime processedAt;
    
    private Map<String, Object> metadata;
    
    // AI Analysis fields (These will be updated by AI Service)
    private String rootCause;
    private String fixSuggestion;
}