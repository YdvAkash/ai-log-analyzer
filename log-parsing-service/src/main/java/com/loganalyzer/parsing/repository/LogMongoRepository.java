package com.loganalyzer.parsing.repository;

import com.loganalyzer.parsing.model.entity.LogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogMongoRepository extends MongoRepository<LogEntity, String> {
    
    // Custom query to find logs by level (INFO, ERROR, etc.)
    List<LogEntity> findByLogLevel(String logLevel);
    
    // Custom query to find logs from a specific source
    List<LogEntity> findBySource(String source);
}