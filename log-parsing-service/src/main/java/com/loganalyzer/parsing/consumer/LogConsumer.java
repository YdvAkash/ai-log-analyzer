package com.loganalyzer.parsing.consumer;

import com.loganalyzer.parsing.model.dto.RawLog;
import com.loganalyzer.parsing.model.entity.ParsedLog;
import com.loganalyzer.parsing.repository.ParsedLogRepository;
import com.loganalyzer.parsing.service.AIAnalysisService;
import com.loganalyzer.parsing.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LogConsumer {

    @Autowired
    private ParsedLogRepository parsedLogRepository;

    @Autowired
    private AIAnalysisService aiAnalysisService;

    @Autowired
    private AlertService alertService;

    @KafkaListener(topics = "raw-logs", groupId = "parsing-group")
    public void listen(RawLog rawLog) {
        log.info("PHASE 2: Received raw log from Kafka with ID: {}", rawLog.getLogId());

        try {
            // 1. Extract Log Level (INFO, ERROR, WARN, FATAL)
            String level = extractLogLevel(rawLog.getRawContent());

            // 2. Prepare ParsedLog Object using Builder
            ParsedLog parsedLog = ParsedLog.builder()
                    .id(rawLog.getLogId())
                    .serviceName(rawLog.getSource())
                    .rawMessage(rawLog.getRawContent())
                    .logLevel(level)
                    .timestamp(rawLog.getTimestamp() != null ? rawLog.getTimestamp() : LocalDateTime.now())
                    .build();

            // 3. AI Analysis - Sirf ERROR aur FATAL logs ke liye AI call karenge
            if ("ERROR".equalsIgnoreCase(level) || "FATAL".equalsIgnoreCase(level)) {
                log.info("Critical log detected. Requesting AI analysis for ID: {}", rawLog.getLogId());
                
                // Gemini API Call
                parsedLog = aiAnalysisService.analyzeWithAI(parsedLog);
                
                // 4. Alerting Logic
                // Note: Agar AI quota fail bhi ho jaye, hum ERROR par mail bhejenge for testing
                if (isCritical(parsedLog)) {
                    log.info("Triggering Alert for Critical/High severity log...");
                    alertService.sendCriticalAlert(parsedLog);
                }
            }

            // 5. Final Save to Elasticsearch
            parsedLogRepository.save(parsedLog);
            log.info("PHASE 2: Successfully indexed parsed log to Elasticsearch for ID: {}", parsedLog.getId());

        } catch (Exception e) {
            log.error("Error processing log in Phase 2: {}", e.getMessage());
        }
    }

    /**
     * AI Severity aur Log Level ke basis par decide karega ki alert bhejna hai ya nahi
     */
    private boolean isCritical(ParsedLog parsedLog) {
        String severity = parsedLog.getAiSeverity();
        String level = parsedLog.getLogLevel();

        // Agar Gemini ne CRITICAL/HIGH bola ho, YA log FATAL ho
        return "CRITICAL".equalsIgnoreCase(severity) || 
               "HIGH".equalsIgnoreCase(severity) || 
               "FATAL".equalsIgnoreCase(level) ||
               ("ERROR".equalsIgnoreCase(level)); // Adding this for testing phase
    }

    /**
     * Simple logic to identify log priority
     */
    private String extractLogLevel(String content) {
        if (content == null) return "INFO";
        String upperContent = content.toUpperCase();
        if (upperContent.contains("ERROR")) return "ERROR";
        if (upperContent.contains("FATAL")) return "FATAL";
        if (upperContent.contains("WARN")) return "WARN";
        return "INFO";
    }
}