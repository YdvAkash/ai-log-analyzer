package com.loganalyzer.alert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alert_history")
public class AlertHistory {
    @Id
    private String id;
    private String logId;
    private String serviceName;
    private String logLevel;
    private String alertType; // e.g., "EMAIL", "SLACK"
    private LocalDateTime sentAt;
    private String status;    // e.g., "SENT", "FAILED"
}