package com.loganalyzer.parsing.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "parsed-logs") // Elasticsearch naya index banayega
public class ParsedLog {

    @Id
    private String id; // Original Log ID (Kafka/Mongo wala)

    @Field(type = FieldType.Keyword)
    private String serviceName; // E.g., Payment-Service, Village-Gov-System

    @Field(type = FieldType.Keyword)
    private String logLevel; // INFO, ERROR, WARN, FATAL

    @Field(type = FieldType.Text)
    private String rawMessage; // Original raw log string

    @Field(type = FieldType.Date)
    private LocalDateTime timestamp;

    // --- AI ANALYSIS FIELDS (The Smart Part) ---

    @Field(type = FieldType.Text)
    private String aiRootCause; // AI batayega error kyun aaya

    @Field(type = FieldType.Text)
    private String aiFixingSteps; // AI batayega steps to fix

    @Field(type = FieldType.Keyword)
    private String aiSeverity; // AI analyze karke batayega (Low, Medium, High, Critical)
}