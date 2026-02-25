package com.loganalyzer.ai.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct; // Ensure you use jakarta for Spring Boot 3

@Slf4j
@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private Client client;

    @PostConstruct
    public void init() {
        try {
            // This manually provides the key to the SDK
            this.client = Client.builder()
                                .apiKey(apiKey)
                                .build();
            log.info("--- Gemini SDK Client Initialized Successfully ---");
        } catch (Exception e) {
            log.error("Failed to initialize Gemini Client: {}", e.getMessage());
        }
    }

    public String getAnalysis(String prompt) {
        try {
            log.info("--- TRIGGERING GEMINI SDK ANALYSIS ---");
            GenerateContentResponse response = client.models.generateContent(
                "gemini-3-flash-preview", 
                prompt, 
                null
            );
            return response.text();
        } catch (Exception e) {
            log.error("Gemini SDK call failed: {}", e.getMessage());
            return "ROOT CAUSE: AI Analysis engine error.\nFIX SUGGESTION: Check API key or network.";
        }
    }
}