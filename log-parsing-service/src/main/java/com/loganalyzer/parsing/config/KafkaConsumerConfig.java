package com.loganalyzer.parsing.config;

import com.loganalyzer.common.dto.LogEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, LogEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        
        // 1. Basic Connection (Sab kuch yahin define karenge)
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "parsing-group-final-v4");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 2. Manual Deserializers create karein (Avoid using .class in props to prevent auto-config conflict)
        StringDeserializer keyDeserializer = new StringDeserializer();
        
        // Value Deserializer setup
        JsonDeserializer<LogEvent> jsonDeserializer = new JsonDeserializer<>(LogEvent.class, false); // false = don't use internal config
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setTypeMapper(new org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper());
        
        // ERROR FIX: "not both" conflict ko rokne ke liye manual object pass kar rahe hain
        ErrorHandlingDeserializer<LogEvent> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LogEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LogEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1); // Pehle 1 thread se test karte hain setup stable karne ke liye
        return factory;
    }
}