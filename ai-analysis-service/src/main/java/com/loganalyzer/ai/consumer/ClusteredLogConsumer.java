package com.loganalyzer.ai.consumer;

import com.loganalyzer.ai.service.AnalysisService;
import com.loganalyzer.common.dto.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ClusteredLogConsumer {

    @Autowired
    private AnalysisService analysisService;

    // In-memory cache to keep track of clusters already analyzed by AI
    // Key: ClusterId
    private final Set<String> analyzedClusters = ConcurrentHashMap.newKeySet();

@KafkaListener(
    topics = "clustered-logs",
    groupId = "ai-analysis-v7"
)
public void consumeClusteredLog(LogEvent event) {
    log.info(".......................AI Consumer Triggered for Log ID: {}........................", event.getLogId());
    
    // Testing Bypass: Agar clusterId null hai, toh hum temporary unique analyze karenge
    String effectiveId = (event.getClusterId() != null) ? event.getClusterId() : event.getLogId();

    if (effectiveId == null) {
        log.warn("Both Log ID and Cluster ID are null. Skipping.");
        return;
    }

    if (!analyzedClusters.contains(effectiveId)) {
        log.info("Initiating AI Analysis for Log/Cluster: [{}]", effectiveId);
        
        try {
            analysisService.analyzeWithAI(event);
            analyzedClusters.add(effectiveId);
        } catch (Exception e) {
            log.error("AI Analysis failed: {}", e.getMessage());
        }
    }
}
}