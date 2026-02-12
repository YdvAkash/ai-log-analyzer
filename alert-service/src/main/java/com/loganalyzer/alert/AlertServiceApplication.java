package com.loganalyzer.alert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableJpaRepositories
public class AlertServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlertServiceApplication.class, args);
    }
}
