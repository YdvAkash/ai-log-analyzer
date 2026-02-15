package com.loganalyzer.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loganalyzer.ai.model.BedrockRequest;
import com.loganalyzer.ai.model.BedrockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.List;

@Slf4j
@Service
public class BedrockService {

    @Autowired
    private BedrockRuntimeClient bedrockClient;

    @Autowired
    private ObjectMapper objectMapper;

    // Claude 3 Haiku model ID (Fast and Cost-effective)
    private static final String MODEL_ID = "anthropic.claude-3-haiku-20240307-v1:0";

    public String getAnalysis(String prompt) {
        try {
            // 1. Prepare Request Payload for Claude 3
            BedrockRequest request = BedrockRequest.builder()
                    .anthropic_version("bedrock-2023-05-31")
                    .max_tokens(500)
                    .temperature(0.5)
                    .messages(List.of(new BedrockRequest.Message("user", 
                              List.of(new BedrockRequest.Content("text", prompt)))))
                    .build();

            String jsonPayload = objectMapper.writeValueAsString(request);

            // 2. Call AWS Bedrock
            InvokeModelRequest invokeRequest = InvokeModelRequest.builder()
                    .modelId(MODEL_ID)
                    .body(SdkBytes.fromUtf8String(jsonPayload))
                    .contentType("application/json")
                    .build();

            InvokeModelResponse response = bedrockClient.invokeModel(invokeRequest);

            // 3. Parse Response
            BedrockResponse bedrockResponse = objectMapper.readValue(
                    response.body().asUtf8String(), BedrockResponse.class);

            return bedrockResponse.getFirstText();

        } catch (Exception e) {
            log.error("Error calling AWS Bedrock: {}", e.getMessage());
            return "Analysis failed due to: " + e.getMessage();
        }
    }
}