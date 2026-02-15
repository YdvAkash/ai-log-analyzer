package com.loganalyzer.ai.repository;

import com.loganalyzer.common.dto.LogEvent;
import com.loganalyzer.ai.model.LogEntity; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LogAnalysisRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Updates an existing log entry in MongoDB with AI-generated insights.
     * We use MongoTemplate here for fine-grained updates.
     */
    public void updateLogWithAIAnalysis(String logId, String rootCause, String fixSuggestion) {
        // 1. Find the document by ID
        Query query = new Query(Criteria.where("_id").is(logId));

        // 2. Prepare the update fields
        Update update = new Update();
        update.set("rootCause", rootCause);
        update.set("fixSuggestion", fixSuggestion);
        update.set("processedAt", java.time.LocalDateTime.now());

        // 3. Update the document in 'processed_logs' collection
        mongoTemplate.updateFirst(query, update, "processed_logs");
    }
}