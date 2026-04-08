package com.loganalyzer.clustering.service;

import com.loganalyzer.clustering.model.ClusterPattern;
import com.loganalyzer.common.dto.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ClusteringService {

    @Autowired
    private PatternMatcher patternMatcher;

    // In-memory storage for patterns: Key is the Masked Pattern String
    private final Map<String, ClusterPattern> activeClusters = new ConcurrentHashMap<>();

    public LogEvent clusterLog(LogEvent event) {
        String rawMessage = event.getRawMessage();
        
        // 1. Generate generic pattern from raw log
        String patternKey = patternMatcher.createPattern(rawMessage);

        // 2. Check if this pattern already exists
        ClusterPattern cluster = activeClusters.computeIfAbsent(patternKey, k -> {
            log.info("New cluster detected for pattern: {}", patternKey);
            return ClusterPattern.builder()
                    .clusterId(UUID.randomUUID().toString())
                    .patternRegex(patternKey)
                    .exampleMessage(rawMessage)
                    .occurrenceCount(0)
                    .firstSeen(LocalDateTime.now())
                    .build();
        });

        // 3. Update cluster stats
        cluster.incrementCount();
        
        // 4. Update the LogEvent with the found/created clusterId
        event.setClusterId(cluster.getClusterId());
        
        log.debug("Assigned Log [{}] to Cluster [{}]", event.getLogId(), cluster.getClusterId());
        
        return event;
    }

    public Map<String, ClusterPattern> getAllClusters() {
        return activeClusters;
    }
}