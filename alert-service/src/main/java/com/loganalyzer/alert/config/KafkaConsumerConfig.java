package com.loganalyzer.alert.config;

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
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        
        // Group ID change kar rahe hain taaki naya offset mile aur purana "bad record" skip ho jaye
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "alert-group-v3");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Deserializer setup (Conflict rokne ke liye props se hata diya hai)
        JsonDeserializer<LogEvent> jsonDeserializer = new JsonDeserializer<>(LogEvent.class, false);
        
        // Sabse important: Trust all packages taaki 'LogEntity' ya kisi bhi class se data aa sake
        jsonDeserializer.addTrustedPackages("*");
        
        // Type mapping ensure karta hai ki agar package name microservices mein alag ho toh bhi chale
        jsonDeserializer.setTypeMapper(new org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper());

        // ErrorHandling wrapper use karein taaki deserialization fail hone par service na rukey
        ErrorHandlingDeserializer<LogEvent> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        return new DefaultKafkaConsumerFactory<>(
            props, 
            new StringDeserializer(), 
            errorHandlingDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LogEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LogEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}