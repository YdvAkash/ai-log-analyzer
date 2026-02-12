package com.loganalyzer.ingestion.service;

import com.loganalyzer.ingestion.model.entity.RawLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
        kafkaTemplate.send(TOPIC, log.getLogId(), log);
    }
}