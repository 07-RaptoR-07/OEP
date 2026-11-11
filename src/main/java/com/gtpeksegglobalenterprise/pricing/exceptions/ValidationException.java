package com.gtpeksegglobalenterprise.pricing.exceptions;

/**
 * Thrown when a request field fails validation.
 * Optionally carries the name of the offending field.
 */
public class ValidationException extends RuntimeException {

    private final String field;

    public ValidationException(String message) {
        this(message, null);
    }

    public ValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
