package com.loganalyzer.parsing.service;

import com.loganalyzer.parsing.model.entity.LogDocument;
import com.loganalyzer.parsing.model.entity.LogEntity;
import com.loganalyzer.parsing.repository.LogElasticRepository;
import com.loganalyzer.parsing.repository.LogMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageService {

    @Autowired
    private LogMongoRepository mongoRepository;

    @Autowired
    private LogElasticRepository elasticRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate; // AI Service ke liye added

    public void saveToAllDatabases(LogEntity entity) {
        try {
            log.info("Attempting to save ID {} to MongoDB", entity.getId());
            mongoRepository.save(entity);
            log.info("Saved to MongoDB: {}", entity.getId());

com.loganalyzer.common.dto.LogEvent aiEvent = new com.loganalyzer.common.dto.LogEvent();
aiEvent.setLogId(entity.getId());
aiEvent.setServiceName(entity.getSource());
aiEvent.setRawMessage(entity.getRawContent());
aiEvent.setLogLevel(entity.getLogLevel());
aiEvent.setClusterId(entity.getId()); // Testing ke liye ID ko hi ClusterId bana dein
aiEvent.setTimestamp(entity.getTimestamp());

kafkaTemplate.send("clustered-logs", entity.getId(), aiEvent);
log.info("Properly mapped event sent to AI Service for Log ID: {}", entity.getId());
log.info("SUCCESS: Trigger sent to AI Analysis Service!...........................................");
            // 2. Save to Elasticsearch
            LogDocument doc = LogDocument.builder()
                    .id(entity.getId())
                    .content(entity.getRawContent())
                    .level(entity.getLogLevel())
                    .source(entity.getSource())
                    .timestamp(entity.getTimestamp())
                    .build();
            
            elasticRepository.save(doc);
            log.info("Indexed in Elasticsearch: {}", entity.getId());

        } catch (Exception e) {
            log.error("Failed to save or trigger AI for ID [{}]: {}", entity.getId(), e.getMessage());
        }
    }
}   