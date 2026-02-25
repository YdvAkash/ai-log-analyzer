package com.loganalyzer.common.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogEvent {
    private String logId;
    private String serviceName;
    private String rawMessage;
    private String logLevel;
    private LocalDateTime timestamp;
    
    // AI Insights
    private String aiRootCause;
    private String aiFixingSteps;
    
    // Clustering & Metadata
    private String clusterId;
    private String environment; // prod, dev, staging
}