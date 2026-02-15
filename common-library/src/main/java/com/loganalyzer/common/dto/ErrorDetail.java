package com.loganalyzer.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String errorCode;    // e.g., "AUTH_001"
    private String fieldName;    // Validation error ke liye (e.g., "email")
    private String debugMessage; // Exception stack trace (only for Dev mode)
}