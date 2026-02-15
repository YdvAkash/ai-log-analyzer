package com.loganalyzer.alert.repository;

import com.loganalyzer.alert.model.AlertHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertHistoryRepository extends MongoRepository<AlertHistory, String> {
    
    // Custom query to find alerts for a specific service
    List<AlertHistory> findByServiceName(String serviceName);
    
    // Custom query to find alerts by log level (e.g., all FATAL alerts)
    List<AlertHistory> findByLogLevel(String logLevel);
}