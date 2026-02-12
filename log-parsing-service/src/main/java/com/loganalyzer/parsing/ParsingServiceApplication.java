package com.loganalyzer.parsing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableElasticsearchRepositories
public class ParsingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParsingServiceApplication.class, args);
    }
}
