package com.loganalyzer.parsing.repository;

import com.loganalyzer.parsing.model.entity.ParsedLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParsedLogRepository extends ElasticsearchRepository<ParsedLog, String> {

    // Custom method: Service name ke basis par logs find karne ke liye
    List<ParsedLog> findByServiceName(String serviceName);

    // Custom method: Sirf specific severity (High/Critical) wale logs dekhne ke liye
    List<ParsedLog> findByAiSeverity(String aiSeverity);
    
    // Custom method: Kisi specific log level (ERROR/WARN) ko filter karne ke liye
    List<ParsedLog> findByLogLevel(String logLevel);
}