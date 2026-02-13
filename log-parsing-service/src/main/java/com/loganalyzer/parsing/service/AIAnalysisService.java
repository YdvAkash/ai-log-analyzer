package com.loganalyzer.parsing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loganalyzer.parsing.model.dto.AIAnalysisResponse;
import com.loganalyzer.parsing.model.entity.ParsedLog;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import software.amazon.awssdk.core.SdkBytes;

@Service
public class AIAnalysisService {

    @Value("${aws.access-key}") private String accessKey;
    @Value("${aws.secret-key}") private String secretKey;
    @Value("${aws.region}") private String region;
    @Value("${aws.bedrock.model-id}") private String modelId;

    public ParsedLog analyzeWithAI(ParsedLog logDetails) {
        try (BedrockRuntimeClient client = BedrockRuntimeClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build()) {

            // Preparing the prompt for Claude
            String prompt = String.format("Analyze this log: %s and return JSON with rootCause, fixingSteps, severity", logDetails.getRawMessage());
            
            JSONObject payload = new JSONObject()
                .put("anthropic_version", "bedrock-2023-05-31")
                .put("max_tokens", 500)
                .put("messages", new org.json.JSONArray().put(new JSONObject().put("role", "user").put("content", prompt)));

            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId(modelId)
                    .contentType("application/json")
                    .body(SdkBytes.fromUtf8String(payload.toString()))
                    .build();

            InvokeModelResponse response = client.invokeModel(request);
            String responseBody = response.body().asUtf8String();
            
            // Extract content from Claude response
            JSONObject responseJson = new JSONObject(responseBody);
            String aiOutput = responseJson.getJSONArray("content").getJSONObject(0).getString("text");
            
            // Map to your DTO (Custom parsing needed here based on AI output)
            logDetails.setAiRootCause(aiOutput); 
            logDetails.setAiSeverity("HIGH");

        } catch (Exception e) {
            logDetails.setAiRootCause("Bedrock Analysis Failed: " + e.getMessage());
        }
        return logDetails;
    }
}