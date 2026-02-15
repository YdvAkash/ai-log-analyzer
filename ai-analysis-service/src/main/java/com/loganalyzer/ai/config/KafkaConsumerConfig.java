package com.loganalyzer.ai.config;

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
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ai-analysis-v7");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // IMPORTANT: 'false' as 2nd param tells Spring NOT to look at YAML props
        JsonDeserializer<LogEvent> jsonDeserializer = new JsonDeserializer<>(LogEvent.class, false);
        jsonDeserializer.addTrustedPackages("*");
        
        // Manual Type Mapping
        jsonDeserializer.setTypeMapper(new org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper());

        // ErrorHandling wrapper
        ErrorHandlingDeserializer<LogEvent> errorDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        return new DefaultKafkaConsumerFactory<>(
            props, 
            new StringDeserializer(), 
            errorDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LogEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LogEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}