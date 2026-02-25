package com.loganalyzer.ingestion.service;

import com.loganalyzer.common.dto.LogEvent;
import com.loganalyzer.common.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, LogEvent> kafkaTemplate;

    public void sendToKafka(LogEvent event) {
        log.info("Pushing log [{}] to topic: {}", event.getLogId(), KafkaTopics.RAW_LOGS);
        
        // Non-blocking send
        kafkaTemplate.send(KafkaTopics.RAW_LOGS, event.getLogId(), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Log [{}] delivered to Kafka offset: {}", event.getLogId(), result.getRecordMetadata().offset());
                } else {
                    log.error("Kafka delivery failed for [{}]: {}", event.getLogId(), ex.getMessage());
                }
            });
    }
}