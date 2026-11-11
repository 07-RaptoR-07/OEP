package com.gtpeksegglobalenterprise.pricing.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gtpeksegglobalenterprise.pricing.exceptions.ValidationException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Maps domain exceptions to HTTP responses.
 * {@link ValidationException} → 400, everything else → 500.
 */
@RestControllerAdvice
class WebExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Map<String, Object>> onValidation(ValidationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "VALIDATION_FAILED");
        body.put("message", ex.getMessage());
        if (ex.getField() != null) {
            body.put("field", ex.getField());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, Object>> onAnythingElse(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "INTERNAL_ERROR");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
