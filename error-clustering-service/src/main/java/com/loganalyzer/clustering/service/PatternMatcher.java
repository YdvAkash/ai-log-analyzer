package com.loganalyzer.clustering.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class PatternMatcher {

    // Regex patterns for dynamic data: UUIDs, IPs, Timestamps, and Numbers
    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    private static final Pattern IP_PATTERN = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}:\\d{2}:\\d{2}");
    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+");

    /**
     * Converts a raw log into a generic pattern by masking dynamic values with '<*>'.
     * Example: "User 123 failed at 2024-01-01" -> "User <*> failed at <*>"
     */
    public String createPattern(String rawMessage) {
        if (rawMessage == null) return "EMPTY_LOG";

        String masked = rawMessage;
        masked = DATE_TIME_PATTERN.matcher(masked).replaceAll("<TIMESTAMP>");
        masked = UUID_PATTERN.matcher(masked).replaceAll("<UUID>");
        masked = IP_PATTERN.matcher(masked).replaceAll("<IP>");
        masked = DIGITS_PATTERN.matcher(masked).replaceAll("<ID>");

        return masked.trim();
    }
}