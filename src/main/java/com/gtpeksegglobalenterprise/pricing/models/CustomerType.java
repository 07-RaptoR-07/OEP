package com.gtpeksegglobalenterprise.pricing.models;

import com.gtpeksegglobalenterprise.pricing.exceptions.ValidationException;

public enum CustomerType {
    VIP(20.0, true),
    EMPLOYEE(30.0, true),
    REGULAR(5.0, true),
    STUDENT(12.0, true),
    PARTNER(18.0, true),
    NON_PROFIT(22.0, true),
    WHOLESALE(35.0, false);

    private final double baseDiscount;
    private final boolean eligibleForBonuses;

    CustomerType(double baseDiscount, boolean eligibleForBonuses) {
        this.baseDiscount = baseDiscount;
        this.eligibleForBonuses = eligibleForBonuses;
    }

    public double getBaseDiscount() { return baseDiscount; }
    public boolean isEligibleForBonuses() { return eligibleForBonuses; }

    public static CustomerType fromString(String val) {
        if (val == null || val.isBlank()) throw new ValidationException("customerType is required", "customerType");
        try {
            return CustomerType.valueOf(val.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown customer type: " + val, "customerType");
        }
    }
}