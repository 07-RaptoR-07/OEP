package com.gtpeksegglobalenterprise.pricing.models;


public enum Country {
    US(8.0), EU(20.0), UK(20.0), CA(13.0), AU(10.0), IN(18.0), BR(15.0), JP(8.0), SG(9.0);

    private final double taxRate;

    Country(double taxRate) { this.taxRate = taxRate; }
    public double getTaxRate() { return taxRate; }

    public static Country fromString(String val) {
        if (val == null || val.isBlank()) return US; 
        try {
            return Country.valueOf(val.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return US; 
        }
    }
    
    
    public static double getSafeTaxRate(String val) {
        if (val == null || val.isBlank()) return US.getTaxRate();
        try {
            return Country.valueOf(val.trim().toUpperCase()).getTaxRate();
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }
}