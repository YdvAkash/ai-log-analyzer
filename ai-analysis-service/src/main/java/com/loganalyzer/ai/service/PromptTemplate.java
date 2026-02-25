package com.loganalyzer.ai.service;

import com.loganalyzer.common.dto.LogEvent;
import org.springframework.stereotype.Component;

@Component
public class PromptTemplate {

    /**
     * Generates a professional system prompt for AWS Bedrock.
     * This guides the AI to behave like a Senior DevOps/SDE Engineer.
     */
    public String buildAnalysisPrompt(LogEvent event) {
        return String.format(
            "SYSTEM: You are an expert Senior Site Reliability Engineer (SRE). " +
            "Analyze the following log event and identify the technical root cause and a specific fix.\n\n" +
            "CONTEXT:\n" +
            "- Service Name: %s\n" +
            "- Log Level: %s\n" +
            "- Environment: %s\n" +
            "- Raw Message: %s\n\n" +
            "INSTRUCTIONS:\n" +
            "1. Be concise and technical.\n" +
            "2. If it's a known exception (like NullPointerException), explain why it happens.\n" +
            "3. Provide exactly two sections in your response:\n\n" +
            "ROOT CAUSE: <Your technical explanation here>\n" +
            "FIX SUGGESTION: <Your step-by-step fix here>",
            
            event.getServiceName(),
            event.getLogLevel(),
            event.getEnvironment() != null ? event.getEnvironment() : "PRODUCTION",
            event.getRawMessage()
        );
    }
}