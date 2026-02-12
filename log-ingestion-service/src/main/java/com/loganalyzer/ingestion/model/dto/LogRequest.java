package com.loganalyzer.ingestion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {
    private String source;
    private String rawContent;
    private String contentType;
    private LocalDateTime timestamp; // Optional: Client bhej sakta hai
    private MetadataDto metadata;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetadataDto {
        private String ipAddress;
        private String hostname;
        private String environment;
    }
}