package com.gtpeksegglobalenterprise.pricing.models;

import com.gtpeksegglobalenterprise.pricing.exceptions.ValidationException;

public enum Campaign {
    HOLIDAY(10.0), 
    WINTER_SALE(8.0), 
    SUMMER(5.0), BLACK_FRIDAY(20.0), 
    BACK_TO_SCHOOL(10.0);

    private final double bonus;

    Campaign(double bonus) { this.bonus = bonus; }
    public double getBonus() { return bonus; }

    public static Campaign fromString(String val) {
        if (val == null || val.isBlank()) return null;
        try {
            return Campaign.valueOf(val.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown campaign: " + val, "campaign");
        }
    }
}