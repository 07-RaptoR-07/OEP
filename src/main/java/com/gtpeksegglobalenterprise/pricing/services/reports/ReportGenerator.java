package com.gtpeksegglobalenterprise.pricing.services.reports;
import com.gtpeksegglobalenterprise.pricing.models.*;

public interface ReportGenerator {
    void generate(Customer customer, Product product, Order order, double discount, double taxRate, double finalPrice);
}