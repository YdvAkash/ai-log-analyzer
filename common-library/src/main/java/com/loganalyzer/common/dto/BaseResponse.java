package com.loganalyzer.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private ErrorDetail error; // Optional error info
    @Builder.Default
    private LocalDateTime responseTime = LocalDateTime.now();

    public static <T> BaseResponse<T> ok(T data, String message) {
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> error(String message, ErrorDetail errorDetail) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(message)
                .error(errorDetail)
                .build();
    }
}