package com.loganalyzer.parsing.service;

import com.loganalyzer.parsing.model.entity.LogDocument;
import com.loganalyzer.parsing.model.entity.LogEntity;
import com.loganalyzer.parsing.repository.LogElasticRepository;
import com.loganalyzer.parsing.repository.LogMongoRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageService {

    @Autowired
    private LogMongoRepository mongoRepository;

    @Autowired
    private LogElasticRepository elasticRepository;

    public void saveToAllDatabases(LogEntity entity) {
        try {
            // 1. Save to MongoDB (Permanent Record)
            log.info("Attempting to save ID {} to collection 'processed_logs'", entity.getId());
            mongoRepository.save(entity);
            log.info("Saved to MongoDB: {}", entity.getId());

            // 2. Save to Elasticsearch (Search Index)
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
            log.error("Failed to save log data for ID [{}]: {}", entity.getId(), e.getMessage());
        }
    }
}