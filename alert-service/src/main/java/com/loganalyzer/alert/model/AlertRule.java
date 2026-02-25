package com.loganalyzer.alert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertRule {
    private String serviceName;
    private String criticalLevel; // e.g., "FATAL"
    private int thresholdCount;   // e.g., 5 errors
    private boolean isEnabled;
}