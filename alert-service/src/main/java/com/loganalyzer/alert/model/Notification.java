package com.loganalyzer.alert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String recipient;
    private String subject;
    private String serviceName;
    private String logLevel;
    private String errorMessage;
    private String clusterId;
    private String rootCause;      // AI se aaya hua data
    private String fixSuggestion;  // AI se aaya hua data
    private LocalDateTime timestamp;
}