package com.loganalyzer.parsing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
// YEH LINE ZAROORI HAI: Taaki config aur consumer beans load ho sakein
@ComponentScan(basePackages = {"com.loganalyzer.parsing", "com.loganalyzer.common"}) 
public class ParsingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParsingServiceApplication.class, args);
    }
}