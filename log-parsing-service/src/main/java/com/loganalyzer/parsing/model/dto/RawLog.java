package com.loganalyzer.parsing.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RawLog {
    private String logId;
    private String source;
    private String rawContent;
    private String contentType;
    private LocalDateTime timestamp;
    private LocalDateTime ingestionTime;
    private Metadata metadata;

    @Data
    public static class Metadata {
        private String ipAddress;
        private String hostname;
        private String environment;
    }
}