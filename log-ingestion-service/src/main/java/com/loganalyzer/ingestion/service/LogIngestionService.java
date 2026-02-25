package com.loganalyzer.ingestion.service;

import com.loganalyzer.common.dto.LogEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogIngestionService {

    @Autowired 
    private KafkaProducerService kafkaProducerService;

    
    public LogEvent ingest(LogEvent event) {
        
        // Sending to Kafka using getLogId()
        kafkaProducerService.sendToKafka(event);

        return event;
    }
}