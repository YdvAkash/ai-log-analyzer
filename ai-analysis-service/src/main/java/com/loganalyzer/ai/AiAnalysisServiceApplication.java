package com.loganalyzer.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(exclude = { 
    org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class 
})
@EnableKafka
public class AiAnalysisServiceApplication {
    public static void main(String[] args) {
        System.out.println("---STEP 1 ------  MAIN----------");
        SpringApplication.run(AiAnalysisServiceApplication.class, args);
    }
}