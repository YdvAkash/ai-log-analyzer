package com.loganalyzer.parsing.model.dto;

import lombok.Data;

@Data
public class AIAnalysisResponse {
    private String rootCause;
    private String fixingSteps;
    private String severity; // Low, Medium, High, Critical
    private String confidenceScore; // AI kitna sure hai (e.g., 0.95)
}