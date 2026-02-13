package com.loganalyzer.ingestion.repository;

import com.loganalyzer.ingestion.model.entity.LogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogSearchRepository extends ElasticsearchRepository<LogDocument, String> {
    // Basic CRUD methods automatically mil jayenge
}