package com.loganalyzer.parsing.service;

import com.loganalyzer.common.dto.LogEvent; // Ensure correct import
import com.loganalyzer.parsing.model.entity.LogEntity;
import com.loganalyzer.parsing.repository.LogMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogMongoRepository logMongoRepository;

    // 1. Return type List<LogEvent> hona chahiye kyunki saare logs mangwa rahe hain
    // 2. Standard method name findAll() use karein
    public List<LogEntity> getAllLogs() {
        return logMongoRepository.findAll();
    }
}