package com.loganalyzer.common.util;

import com.loganalyzer.common.constants.AppConstants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for standardized date and time operations across microservices.
 */
public class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = 
        DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);

    /**
     * Converts LocalDateTime to a standard String format.
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : null;
    }

    /**
     * Parses a String timestamp into a LocalDateTime object.
     * Falls back to current time if parsing fails to prevent system crash.
     */
    public static LocalDateTime parse(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, DEFAULT_FORMATTER);
        } catch (DateTimeParseException e) {
            // Log warning in real service, returning current time as fallback
            return LocalDateTime.now();
        }
    }

    /**
     * Generates a current timestamp in LocalDateTime format.
     */
    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}