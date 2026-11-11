package com.gtpeksegglobalenterprise.pricing.services.reports;

import com.gtpeksegglobalenterprise.pricing.models.Customer;
import com.gtpeksegglobalenterprise.pricing.models.Order;
import com.gtpeksegglobalenterprise.pricing.models.Product;

public class XmlReportGenerator implements ReportGenerator {
    @Override
    public void generate(Customer customer, Product product, Order order, double discount, double taxRate, double finalPrice) {
        System.out.println("<report><finalPrice>" + finalPrice + "</finalPrice></report>");
    }
}