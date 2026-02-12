package com.loganalyzer.ingestion.service;

import com.loganalyzer.ingestion.exception.InvalidLogException;
import com.loganalyzer.ingestion.model.dto.LogRequest;
import com.loganalyzer.ingestion.model.entity.RawLog;
import com.loganalyzer.ingestion.model.entity.RawLog.Metadata;
import com.loganalyzer.ingestion.repository.RawLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LogIngestionService {

    @Autowired
    private RawLogRepository repository;

    @Autowired 
    private KafkaProducerService kafkaProducerService;

    public RawLog processAndSave(LogRequest request) {
        // 1. Validation
        if (request.getSource() == null || request.getSource().isEmpty()) {
            throw new InvalidLogException("Source field cannot be empty!");
        }
        
        // 2. Mapping DTO to Entity
        RawLog entity = new RawLog();
        entity.setLogId(UUID.randomUUID().toString());
        entity.setSource(request.getSource());
        entity.setRawContent(request.getRawContent());
        entity.setContentType(request.getContentType());
        
        entity.setTimestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now());
        entity.setIngestionTime(LocalDateTime.now());

        if (request.getMetadata() != null) {
            Metadata meta = new Metadata();
            meta.setIpAddress(request.getMetadata().getIpAddress());
            meta.setHostname(request.getMetadata().getHostname());
            meta.setEnvironment(request.getMetadata().getEnvironment());
            entity.setMetadata(meta);
        }

        // 3. Pehle MongoDB mein save karein
        RawLog savedLog = repository.save(entity);

        // 4. Ab Kafka mein push karein (savedLog use karke)
        try {
            kafkaProducerService.sendRawLog(savedLog);
        } catch (Exception e) {
            // Hum system ko crash nahi karenge agar Kafka down hai, 
            // kyunki data DB mein safe hai.
            System.err.println("CRITICAL: MongoDB saved but Kafka Push Failed: " + e.getMessage());
        }

        return savedLog;
    }
}