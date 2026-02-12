package com.loganalyzer.common.constants;

public class KafkaTopics {
    public static final String RAW_LOGS = "raw-logs";
    public static final String PARSED_LOGS = "parsed-logs";
    public static final String ANALYSIS_REQUESTS = "analysis-requests";
    public static final String ANALYSIS_RESULTS = "analysis-results";
    public static final String ALERTS = "alerts";
    
    private KafkaTopics() {}
}
