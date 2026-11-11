package com.gtpeksegglobalenterprise.pricing.services.notifications;

import com.gtpeksegglobalenterprise.pricing.models.Customer;
import com.gtpeksegglobalenterprise.pricing.models.Order;
import com.gtpeksegglobalenterprise.pricing.models.Product;

public class EmailNotificationChannel implements NotificationChannel {
    @Override
    public void notify(Customer customer, Product product, Order order, double discount, double finalPrice) {
        System.out.println("=== EMAIL NOTIFICATION ===");
        System.out.println("To      : " + customer.getCustomerId() + "@example.com");
        System.out.println("Final price: " + finalPrice);
        System.out.println("==========================");
    }
}