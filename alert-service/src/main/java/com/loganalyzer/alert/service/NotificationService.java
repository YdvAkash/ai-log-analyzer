package com.loganalyzer.alert.service;

import com.loganalyzer.common.dto.LogEvent;

public interface NotificationService {
    void sendErrorAlert(LogEvent event);
}