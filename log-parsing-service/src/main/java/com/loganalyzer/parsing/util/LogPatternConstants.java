package com.loganalyzer.parsing.util;

import java.util.regex.Pattern;

public class LogPatternConstants {

    // Regex to match common log levels: ERROR, INFO, WARN, DEBUG, FATAL
    public static final Pattern LOG_LEVEL_PATTERN = 
        Pattern.compile("\\b(ERROR|INFO|WARN|DEBUG|FATAL)\\b", Pattern.CASE_INSENSITIVE);

    // Regex to extract Timestamp from logs (supports formats like 2026-02-13T...)
    public static final Pattern TIMESTAMP_PATTERN = 
        Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");

    // AI Prompt Template: Ye AI ko context dene ke liye use hoga
    public static final String AI_PROMPT_TEMPLATE = """
        Analyze the following application log and provide a structured JSON response.
        Log Content: %s
        Service Name: %s
        
        The response must include:
        1. rootCause: Brief explanation of why this happened.
        2. fixingSteps: Bullet points to resolve the issue.
        3. severity: Low, Medium, High, or Critical.
        
        Return ONLY valid JSON.
        """;
}