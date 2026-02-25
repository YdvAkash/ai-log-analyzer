package com.loganalyzer.ingestion.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
@Document(indexName = "logs_index") // Elasticsearch Index Name
public class LogDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String content; // Full-text search ke liye

    @Field(type = FieldType.Keyword)
    private String source; // Exact match filter ke liye

    @Field(type = FieldType.Date)
    private LocalDateTime timestamp;
}