package com.gtpeksegglobalenterprise.pricing.models;

import com.gtpeksegglobalenterprise.pricing.exceptions.ValidationException;

public enum NotificationType {
    EMAIL, SMS;

    public static NotificationType fromString(String val) {
        if (val == null || val.isBlank()) throw new ValidationException("notificationType is required", "notificationType");
        try {
            return NotificationType.valueOf(val.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown notification type: " + val, "notificationType");
        }
    }
}