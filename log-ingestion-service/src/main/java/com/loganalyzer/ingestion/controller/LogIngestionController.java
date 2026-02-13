package com.loganalyzer.ingestion.controller;

import com.loganalyzer.ingestion.model.dto.LogRequest;
import com.loganalyzer.ingestion.model.dto.LogResponse;
import com.loganalyzer.ingestion.model.entity.RawLog;
import com.loganalyzer.ingestion.service.LogIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;

@RestController
@RequestMapping("/api/v1/logs")
public class LogIngestionController {

    @Autowired
    private LogIngestionService ingestionService;

    /**
     * POST /api/v1/logs
     * Client se LogRequest DTO leta hai aur LogResponse return karta hai.
     */
@PostMapping
public ResponseEntity<LogResponse> ingestLog(@RequestBody LogRequest request) {
    
    RawLog savedLog = ingestionService.processAndSave(request);

    LogResponse response = LogResponse.builder()
            .logId(savedLog.getLogId())
            .source(savedLog.getSource())
            .status("SUCCESS")
            .ingestionTime(savedLog.getIngestionTime()) // Direct use
            .message("Log safely persisted in MongoDB")
            .build();

    return new ResponseEntity<>(response, HttpStatus.CREATED);
}
}