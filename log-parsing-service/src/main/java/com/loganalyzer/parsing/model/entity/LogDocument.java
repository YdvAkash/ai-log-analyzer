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
@Document(indexName = "logs") // Elasticsearch Index Name
public class LogDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "content")
    private String content;

    @Field(type = FieldType.Keyword, name = "level")
    private String level;

    @Field(type = FieldType.Keyword, name = "source")
    private String source;

    @Field(type = FieldType.Date, name = "timestamp")
    private LocalDateTime timestamp;
}