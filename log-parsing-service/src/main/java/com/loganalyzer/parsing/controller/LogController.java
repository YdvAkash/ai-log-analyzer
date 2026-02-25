package com.loganalyzer.parsing.controller;

import com.loganalyzer.common.dto.LogEvent;
import com.loganalyzer.parsing.model.entity.LogEntity;
import com.loganalyzer.parsing.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parsing")
// Frontend connect karne ke liye CrossOrigin zaroori hai (agar gateway use nahi kar rahe toh)
@CrossOrigin(origins = "http://localhost:5173") 
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/all")
    public ResponseEntity<List<LogEntity>> getAll() {
        // Service se saari list mangwayein
        List<LogEntity> logs = logService.getAllLogs();
        
        // Agar list khali hai toh 204 No Content, warna 200 OK
        if (logs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}