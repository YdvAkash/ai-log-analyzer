package com.loganalyzer.clustering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableJpaRepositories
public class ClusteringServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClusteringServiceApplication.class, args);
    }
}
