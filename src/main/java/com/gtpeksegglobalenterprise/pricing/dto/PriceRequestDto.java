package com.gtpeksegglobalenterprise.pricing.dto;

/**
 * Incoming price-calculation request.
 *
 * <p>
 * Field names map directly to the JSON wire format. Do not rename, remove,
 * or change the type of any field — doing so breaks the API contract with
 * callers without any compile-time warning.
 *
 * <p>
 * Supported values:
 * <ul>
 * <li>{@code customerType} — {@code VIP}, {@code EMPLOYEE},
 * {@code REGULAR}</li>
 * <li>{@code productCategory} — {@code ELECTRONICS}, {@code CLOTHING},
 * {@code BOOK}</li>
 * <li>{@code campaign} — {@code HOLIDAY}, {@code WINTER_SALE} (optional)</li>
 * <li>{@code reportFormat} — {@code JSON}, {@code PDF}, {@code CSV}</li>
 * <li>{@code notificationType} — {@code EMAIL}, {@code SMS}</li>
 * <li>{@code country} — {@code US}, {@code EU}, {@code UK}, {@code CA}</li>
 * </ul>
 */
public class PriceRequestDto {

    private String customerId;
    private String customerType;
    private String productCategory;
    private double basePrice;
    private String country;
    private String campaign;
    private String reportFormat;
    private String notificationType;
    private int loyaltyPoints;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String v) {
        this.customerId = v;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String v) {
        this.customerType = v;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String v) {
        this.productCategory = v;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double v) {
        this.basePrice = v;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String v) {
        this.country = v;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String v) {
        this.campaign = v;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String v) {
        this.reportFormat = v;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String v) {
        this.notificationType = v;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int v) {
        this.loyaltyPoints = v;
    }
}
