package com.gtpeksegglobalenterprise.pricing.models;

/**
 * Represents a product being priced.
 */
public class Product {
    
    private ProductCategory category;
    private double basePrice;

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory v) { this.category = v; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double v) { this.basePrice = v; }
}