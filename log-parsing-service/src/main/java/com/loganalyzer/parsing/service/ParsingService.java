package com.loganalyzer.parsing.service;

import com.loganalyzer.common.dto.LogEvent;
import com.loganalyzer.parsing.model.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ParsingService {

    @Autowired
    private StorageService storageService;

    private static final Pattern LEVEL_PATTERN = Pattern.compile("\\b(ERROR|INFO|WARN|DEBUG|FATAL)\\b", Pattern.CASE_INSENSITIVE);

    public void processLog(LogEvent event) {
        log.info("Parsing log for service: {}", event.getServiceName());

        // Using new field names from LogEvent
        String rawText = event.getRawMessage(); 
        String level = extractLogLevel(rawText);

        LogEntity entity = LogEntity.builder()
                .id(event.getLogId())
                .source(event.getServiceName()) // Mapped from serviceName
                .rawContent(rawText)            // Mapped from rawMessage
                .logLevel(level != null ? level : event.getLogLevel())
                .timestamp(event.getTimestamp())
                .processedAt(LocalDateTime.now())
                .build();

        storageService.saveToAllDatabases(entity);
    }

    private String extractLogLevel(String content) {
        if (content == null) return "INFO";
        Matcher matcher = LEVEL_PATTERN.matcher(content);
        return matcher.find() ? matcher.group(1).toUpperCase() : "INFO";
    }
}