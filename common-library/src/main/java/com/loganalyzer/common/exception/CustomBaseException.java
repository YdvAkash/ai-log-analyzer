package com.loganalyzer.common.exception;

import lombok.Getter;

/**
 * Base exception class for all custom exceptions in the AI Log Analyzer system.
 */
@Getter
public class CustomBaseException extends RuntimeException {

    private final String errorCode;

    public CustomBaseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}