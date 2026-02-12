package com.loganalyzer.ingestion.exception;

public class InvalidLogException extends RuntimeException {
    public InvalidLogException(String message) {
        super(message);
    }
}