package com.loganalyzer.ingestion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.loganalyzer.ingestion.model.entity.RawLog;

public interface RawLogRepository extends MongoRepository<RawLog, String> {
    
}
