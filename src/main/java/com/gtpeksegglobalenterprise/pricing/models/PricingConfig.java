package com.gtpeksegglobalenterprise.pricing.models;

/**
 * Holds the runtime limits that govern how the pricing engine behaves.
 */
public class PricingConfig {

    private double maxTotalDiscount = 50.0;
    private double largeTransactionThreshold = 500.0;

    public double getMaxTotalDiscount()              { return maxTotalDiscount; }
    public void setMaxTotalDiscount(double v)        { this.maxTotalDiscount = v; }

    public double getLargeTransactionThreshold()     { return largeTransactionThreshold; }
    public void setLargeTransactionThreshold(double v) { this.largeTransactionThreshold = v; }
}
