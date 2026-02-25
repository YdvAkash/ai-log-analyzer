package com.loganalyzer.parsing.repository;

import com.loganalyzer.parsing.model.entity.LogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogElasticRepository extends ElasticsearchRepository<LogDocument, String> {
    
    // Elasticsearch specific search: search in the content field
    List<LogDocument> findByContentContaining(String keyword);
}