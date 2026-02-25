package com.loganalyzer.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedrockRequest {
    private String anthropic_version; // Usually "bedrock-2023-05-31"
    private int max_tokens;
    private List<Message> messages;
    private double temperature;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;    // "user" or "assistant"
        private List<Content> content;
    }

    @Data
    @AllArgsConstructor
    public static class Content {
        private String type;    // "text"
        private String text;
    }
}