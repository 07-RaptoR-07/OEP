package com.gtpeksegglobalenterprise.pricing.services.notifications;

import com.gtpeksegglobalenterprise.pricing.models.Customer;
import com.gtpeksegglobalenterprise.pricing.models.Order;
import com.gtpeksegglobalenterprise.pricing.models.Product;

public interface NotificationChannel {
    void notify(Customer customer, Product product, Order order, double discount, double finalPrice);
}