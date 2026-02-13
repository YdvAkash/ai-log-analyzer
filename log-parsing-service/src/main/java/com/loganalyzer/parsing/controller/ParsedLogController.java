package com.loganalyzer.parsing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loganalyzer.parsing.model.entity.ParsedLog;
import com.loganalyzer.parsing.repository.ParsedLogRepository;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*") // React se connect karne ke liye zaroori hai
public class ParsedLogController {

    @Autowired
    private ParsedLogRepository repository;

    @GetMapping("/all")
    public Iterable<ParsedLog> getAllLogs() {
        // Latest logs upar dikhane ke liye aap ise sort bhi kar sakte hain
        return repository.findAll();
    }
}