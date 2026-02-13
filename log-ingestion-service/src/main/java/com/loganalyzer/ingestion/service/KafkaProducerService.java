package com.loganalyzer.ingestion.service;

import com.loganalyzer.ingestion.model.entity.RawLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "raw-logs";

    @Autowired
    private KafkaTemplate<String, RawLog> kafkaTemplate;

    public void sendRawLog(RawLog log) {
        logger.info("Pushing log to Kafka topic: {}", TOPIC);
        // Message ko Kafka topic mein bhej rahe hain
        // Key: logId, Value: RawLog object
        CompletableFuture<SendResult<String, RawLog>> future = kafkaTemplate.send(TOPIC, log.getLogId(), log);

        // Attach async callbacks so failures are visible in logs
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                logger.error("Failed to send log {} to Kafka topic {}: {}", log.getLogId(), TOPIC, ex.getMessage(), ex);
            } else if (result != null && result.getRecordMetadata() != null) {
                logger.info("Successfully sent log {} to topic {} partition={} offset={}",
                        log.getLogId(), TOPIC, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            } else {
                logger.info("Successfully sent log {} to topic {} (no metadata)", log.getLogId(), TOPIC);
            }
        });

        // Also do a short synchronous wait to surface errors (helps during debugging).
        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Kafka send wait failed for log {}: {}", log.getLogId(), e.getMessage(), e);
        }
    }
}