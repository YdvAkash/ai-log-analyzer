package com.loganalyzer.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableMongoRepositories
public class AiAnalysisServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiAnalysisServiceApplication.class, args);
    }
}
