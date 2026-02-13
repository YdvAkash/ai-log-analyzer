package com.loganalyzer.parsing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableKafka
@EnableElasticsearchRepositories
@EnableAsync // Yeh background email sending ke liye zaroori hai
public class ParsingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParsingServiceApplication.class, args);
    }
}
