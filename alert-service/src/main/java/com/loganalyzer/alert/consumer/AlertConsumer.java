package com.loganalyzer.alert.consumer;

import com.loganalyzer.alert.service.NotificationService;
import com.loganalyzer.common.dto.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlertConsumer {

    @Autowired
    private NotificationService notificationService;

    /**
     * Consumes logs from the 'clustered-logs' or 'ai-analysis-results' topic.
     * Triggers an alert if the log severity is high.
     */
    @KafkaListener(
        topics = "clustered-logs", 
        groupId = "alert-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeForAlerts(LogEvent event) {
        log.info("Alert Service checking log: ID [{}], Level [{}]", 
                 event.getLogId(), event.getLogLevel());

        // 1. Alert only on high severity levels
        if (isCritical(event.getLogLevel())) {
            log.warn("🚨 Critical issue detected in service: {}", event.getServiceName());
            
            try {
                // 2. Send Email/Notification
                notificationService.sendErrorAlert(event);
            } catch (Exception e) {
                log.error("Failed to send alert for log [{}]: {}", 
                          event.getLogId(), e.getMessage());
            }
        }
    }

    /**
     * Logic to filter logs for alerting.
     */
    private boolean isCritical(String logLevel) {
        if (logLevel == null) return false;
        String level = logLevel.toUpperCase();
        return level.equals("ERROR") || level.equals("FATAL") || level.equals("CRITICAL");
    }
}