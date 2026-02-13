package com.loganalyzer.parsing.service;

import com.loganalyzer.parsing.util.LogPatternConstants;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;

@Service
public class ParsingService {

    public String extractLevel(String rawContent) {
        Matcher matcher = LogPatternConstants.LOG_LEVEL_PATTERN.matcher(rawContent);
        if (matcher.find()) {
            return matcher.group(1).toUpperCase();
        }
        return "INFO"; // Default level
    }

    // Aap yahan Error Codes ya IP addresses extract karne ka logic bhi likh sakte hain
}