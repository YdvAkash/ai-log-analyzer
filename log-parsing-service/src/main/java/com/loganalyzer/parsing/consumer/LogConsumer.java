package com.loganalyzer.parsing.consumer;

import com.loganalyzer.common.constants.KafkaTopics;
import com.loganalyzer.common.dto.LogEvent;
import com.loganalyzer.parsing.service.ParsingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogConsumer {

    @Autowired
    private ParsingService parsingService;

    /**
     * Listens to the 'raw-logs' topic. 
     * Uses the common-library LogEvent DTO for seamless data transfer.
     */
    @KafkaListener(
        topics = KafkaTopics.RAW_LOGS, 
        groupId = "parsing-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeRawLog(LogEvent rawEvent) {
        // ERROR FIX: Use getServiceName() instead of getSource()
        log.info("Received raw log from Kafka: ID [{}], Service [{}]", 
                 rawEvent.getLogId(), rawEvent.getServiceName());

        try {
            // Processing logic shuru karte hain
            parsingService.processLog(rawEvent);
        } catch (Exception e) {
            log.error("Error while processing log [{}]: {}", 
                      rawEvent.getLogId(), e.getMessage());
        }
    }
}