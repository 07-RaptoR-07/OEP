package com.gtpeksegglobalenterprise.pricing.models;

import com.gtpeksegglobalenterprise.pricing.exceptions.ValidationException;

public enum ProductCategory {
    ELECTRONICS(25.0),
    CLOTHING(30.0),
    BOOK(40.0),
    FOOD(5.0),
    MEDICINE(0.0);

    private final double maxDiscount;

    ProductCategory(double maxDiscount) { this.maxDiscount = maxDiscount; }
    public double getMaxDiscount() { return maxDiscount; }

    public static ProductCategory fromString(String val) {
        if (val == null || val.isBlank()) throw new ValidationException("productCategory is required", "productCategory");
        try {
            return ProductCategory.valueOf(val.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown product category: " + val, "productCategory");
        }
    }
}