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
        groupId = "ai-analysis-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeClusteredLog(LogEvent event) {
        String clusterId = event.getClusterId();

        // 1. Check if clusterId is null (Safety check)
        if (clusterId == null) {
            log.warn("Log [{}] has no Cluster ID. Skipping AI analysis.", event.getLogId());
            return;
        }

        // 2. Optimization: Analyze only if this is a new cluster
        if (!analyzedClusters.contains(clusterId)) {
            log.info("New Cluster detected [{}]. Initiating AWS Bedrock Analysis...", clusterId);
            
            try {
                // Call AI Service to get Root Cause and Fix
                analysisService.analyzeWithAI(event);
                
                // Add to analyzed set so we don't call AI for the same error pattern again
                analyzedClusters.add(clusterId);
                
            } catch (Exception e) {
                log.error("AI Analysis failed for cluster [{}]: {}", clusterId, e.getMessage());
            }
        } else {
            log.debug("Cluster [{}] already analyzed. Skipping duplicate AI call.", clusterId);
            // Optional: Update the existing DB record with a reference to the previous analysis
        }
    }
}