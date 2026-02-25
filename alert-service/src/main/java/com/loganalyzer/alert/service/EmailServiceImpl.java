package com.loganalyzer.alert.service;

import com.loganalyzer.alert.model.AlertHistory;
import com.loganalyzer.alert.repository.AlertHistoryRepository;
import com.loganalyzer.common.dto.LogEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private AlertHistoryRepository alertRepository;

    private final String developerEmail = "ay2484900@gmail.com"; // Akash, aapka email

    @Override
    public void sendErrorAlert(LogEvent event) {
        try {
            log.info("Preparing email alert for service: {}", event.getServiceName());

            // 1. Prepare Thymeleaf Context (Data for HTML)
            Context context = new Context();
            context.setVariable("serviceName", event.getServiceName());
            context.setVariable("logLevel", event.getLogLevel());
            context.setVariable("message", event.getRawMessage());
            context.setVariable("timestamp", LocalDateTime.now().toString());
            // In future, you can fetch AI root cause from MongoDB here

            // 2. Create HTML Body from Template
            String htmlContent = templateEngine.process("alert-email", context);

            // 3. Send Email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            
            helper.setText(htmlContent, true);
            helper.setTo(developerEmail);
            helper.setSubject("🚨 CRITICAL ALERT: " + event.getServiceName());
            helper.setFrom("log-analyzer@system.com");

            mailSender.send(mimeMessage);

            // 4. Save to History
            saveAlertHistory(event, "SENT");
            log.info("Alert email sent successfully to {}", developerEmail);

        } catch (Exception e) {
            log.error("Failed to send email alert: {}", e.getMessage());
            saveAlertHistory(event, "FAILED");
        }
    }

    private void saveAlertHistory(LogEvent event, String status) {
        AlertHistory history = AlertHistory.builder()
                .logId(event.getLogId())
                .serviceName(event.getServiceName())
                .logLevel(event.getLogLevel())
                .alertType("EMAIL")
                .sentAt(LocalDateTime.now())
                .status(status)
                .build();
        alertRepository.save(history);
    }
}