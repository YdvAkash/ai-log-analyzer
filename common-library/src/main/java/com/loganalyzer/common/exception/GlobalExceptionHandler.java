package com.loganalyzer.common.exception;

import com.loganalyzer.common.dto.BaseResponse;
import com.loganalyzer.common.dto.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles our custom application-specific exceptions.
     */
    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomBaseException ex) {
        log.error("Custom Exception: Code [{}], Message: {}", ex.getErrorCode(), ex.getMessage());
        
        ErrorDetail errorDetail = new ErrorDetail(ex.getErrorCode(), null, ex.getMessage());
        BaseResponse<Object> response = BaseResponse.error(ex.getMessage(), errorDetail);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch-all for any other runtime exceptions to prevent leaking internal details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(Exception ex) {
        log.error("Unhandled Exception: ", ex);
        
        ErrorDetail errorDetail = new ErrorDetail("INTERNAL_SERVER_ERROR", null, "An unexpected error occurred");
        BaseResponse<Object> response = BaseResponse.error("Internal Server Error", errorDetail);
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}