package com.gtpeksegglobalenterprise.pricing.services.notifications;

import com.gtpeksegglobalenterprise.pricing.models.Customer;
import com.gtpeksegglobalenterprise.pricing.models.Order;
import com.gtpeksegglobalenterprise.pricing.models.Product;

public class SmsNotificationChannel implements NotificationChannel {
    @Override
    public void notify(Customer customer, Product product, Order order, double discount, double finalPrice) {
        System.out.println("=== SMS NOTIFICATION ===");
        System.out.println("Msg : ACME quote: " + finalPrice);
        System.out.println("========================");
    }
}