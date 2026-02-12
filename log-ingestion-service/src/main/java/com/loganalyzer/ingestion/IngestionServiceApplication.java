package com.loganalyzer.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableMongoRepositories
public class IngestionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngestionServiceApplication.class, args);
    }
}
