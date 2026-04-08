package com.loganalyzer.clustering.consumer;

import com.loganalyzer.clustering.service.ClusteringService;
import com.loganalyzer.common.dto.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ParsedLogConsumer {

    @Autowired
    private ClusteringService clusteringService;

    @Autowired
    private KafkaTemplate<String, LogEvent> kafkaTemplate;

    private static final String INPUT_TOPIC = "parsed-logs";
    private static final String OUTPUT_TOPIC = "clustered-logs";

    /**
     * Consumes parsed logs from the 'parsed-logs' topic.
     * Identifies clusters and pushes updated events to 'clustered-logs'.
     */
    @KafkaListener(
        topics = INPUT_TOPIC,
        groupId = "clustering-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeParsedLog(LogEvent event) {
        log.info("Processing log for clustering: ID [{}], Service [{}]", 
                 event.getLogId(), event.getServiceName());

        try {
            // 1. Process clustering logic
            LogEvent clusteredEvent = clusteringService.clusterLog(event);

            // 2. Push to next topic for AI Analysis or Alerting
            kafkaTemplate.send(OUTPUT_TOPIC, clusteredEvent.getLogId(), clusteredEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Clustered log [{}] pushed to topic: {}", 
                                 clusteredEvent.getLogId(), OUTPUT_TOPIC);
                    } else {
                        log.error("Failed to push clustered log: {}", ex.getMessage());
                    }
                });

        } catch (Exception e) {
            log.error("Error in clustering flow for log [{}]: {}", 
                      event.getLogId(), e.getMessage());
        }
    }
}