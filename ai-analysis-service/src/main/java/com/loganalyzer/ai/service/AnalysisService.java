package com.loganalyzer.ai.service;

import com.loganalyzer.ai.repository.LogAnalysisRepository;
import com.loganalyzer.common.dto.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnalysisService {

    @Autowired
    private BedrockService bedrockService;

    @Autowired
    private LogAnalysisRepository repository;

    @Autowired
    private PromptTemplate promptTemplate; // Template inject kiya gaya hai

    /**
     * Orchestrates the AI analysis flow.
     */
    public void analyzeWithAI(LogEvent event) {
        log.info("Starting AI Analysis for Log ID: {} using AWS Bedrock", event.getLogId());

        try {
            // 1. Construct the structured prompt using the Template
            String prompt = promptTemplate.buildAnalysisPrompt(event);

            // 2. Get AI Response from Bedrock (Claude 3 / Llama 3)
            String aiResponse = bedrockService.getAnalysis(prompt);

            // 3. Parse and Save the insights to MongoDB
            parseAndSave(event.getLogId(), aiResponse);

        } catch (Exception e) {
            log.error("AI Analysis flow interrupted for Log [{}]: {}", event.getLogId(), e.getMessage());
        }
    }

    /**
     * Parses the raw AI string and updates the existing document in MongoDB.
     */
    private void parseAndSave(String logId, String aiResponse) {
        String rootCause = "Detection in progress...";
        String fix = "Reviewing system architecture.";

        try {
            // Parsing logic based on the 'ROOT CAUSE:' and 'FIX SUGGESTION:' labels
            if (aiResponse.contains("ROOT CAUSE:") && aiResponse.contains("FIX SUGGESTION:")) {
                String[] parts = aiResponse.split("FIX SUGGESTION:");
                rootCause = parts[0].replace("ROOT CAUSE:", "").trim();
                fix = parts[1].trim();
            } else {
                // Fallback: If AI gives a direct answer without headers
                rootCause = aiResponse.substring(0, Math.min(aiResponse.length(), 500));
            }

            // Update the MongoDB collection via the repository
            repository.updateLogWithAIAnalysis(logId, rootCause, fix);
            log.info("Successfully persisted AI insights for Log ID: {}", logId);

        } catch (Exception e) {
            log.error("Parsing failed for AI response on Log [{}]. Response received: {}", logId, aiResponse);
        }
    }
}