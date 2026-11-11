package com.gtpeksegglobalenterprise.pricing.models;

import com.gtpeksegglobalenterprise.pricing.exceptions.ValidationException;

public enum ReportFormat {
    JSON,
    PDF, 
    CSV, 
    XML, 
    MARKDOWN;

    public static ReportFormat fromString(String val) {
        if (val == null || val.isBlank()) throw new ValidationException("reportFormat is required", "reportFormat");
        try {
            return ReportFormat.valueOf(val.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown report format: " + val, "reportFormat");
        }
    }
}