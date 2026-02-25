package com.loganalyzer.ingestion.model.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder // Builder pattern se object banana easy ho jata hai
public class LogResponse {
    private String logId;
    private String source;
    private String status; // e.g., "SUCCESS"
    private LocalDateTime ingestionTime;
    private String message;
}