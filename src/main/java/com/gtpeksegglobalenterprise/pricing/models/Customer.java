package com.gtpeksegglobalenterprise.pricing.models;
import java.util.List;
/**
 * Represents a customer making a purchase.
 */
public class Customer {

    private String customerId;
    private CustomerType type;
    private String countryCode;
    private List<NotificationType> notificationChannels;
    private int loyaltyPoints;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String v) { this.customerId = v; }

    public CustomerType getType() { return type; }
    public void setType(CustomerType v) { this.type = v; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String v) { this.countryCode = v; }

   public List<NotificationType> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<NotificationType> v) { this.notificationChannels = v; }

    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int v) { this.loyaltyPoints = v; }
}
