package com.loganalyzer.ingestion.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "raw_logs")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawLog {
    @Id
    private String id;

    private String logId;

    private String source;

    private LocalDateTime timestamp;

    private String rawContent;

    private String contentType;

    private LocalDateTime ingestionTime; // Changed to LocalDateTime for consistency

    private Metadata metadata;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {
        private String ipAddress;
        private String hostname;
        private String environment;
    }

    // DELETE THIS METHOD ENTIRELY:
    // public void setTimestamp(Object timestamp2) { ... }
}