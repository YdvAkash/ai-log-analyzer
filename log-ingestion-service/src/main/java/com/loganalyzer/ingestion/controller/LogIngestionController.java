package com.loganalyzer.ingestion.controller;

import com.loganalyzer.common.dto.LogEvent;
import com.loganalyzer.ingestion.service.LogIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingest")
public class LogIngestionController {

    @Autowired
    private LogIngestionService ingestionService;

    @PostMapping("/log")
    public ResponseEntity<LogEvent> ingestLog(@RequestBody LogEvent request) {
        // Metadata enrichment using new LogEvent fields
        if (request.getLogId() == null) request.setLogId(UUID.randomUUID().toString());
        if (request.getTimestamp() == null) request.setTimestamp(LocalDateTime.now());
        if (request.getEnvironment() == null) request.setEnvironment("DEV");

        LogEvent processed = ingestionService.ingest(request);
        return new ResponseEntity<>(processed, HttpStatus.ACCEPTED);
    }
}