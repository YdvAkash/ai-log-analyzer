package com.loganalyzer.clustering.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterPattern {
    
    private String clusterId;     // Unique ID for this group of logs
    private String patternRegex;  // The "Masked" version of the log (e.g., "Failed to connect to *")
    private String exampleMessage; // One real example of this error for reference
    private int occurrenceCount;  // How many times this pattern has appeared
    private LocalDateTime firstSeen;
    private LocalDateTime lastSeen;

    /**
     * Increments the count whenever a new log matches this pattern.
     */
    public void incrementCount() {
        this.occurrenceCount++;
        this.lastSeen = LocalDateTime.now();
    }
}