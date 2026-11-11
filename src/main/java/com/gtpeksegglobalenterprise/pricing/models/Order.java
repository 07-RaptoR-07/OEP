package com.gtpeksegglobalenterprise.pricing.models;
import java.util.List;
/**
 * Represents a purchase order, capturing the transaction-level context
 * that is not intrinsic to the customer or the product.
 */
public class Order {
    private List<Campaign> campaigns;
    private ReportFormat reportFormat;

    public List<Campaign> getCampaigns() { return campaigns; }
    public void setCampaigns(List<Campaign> v) { this.campaigns = v; }

    public ReportFormat getReportFormat() { return reportFormat; }
    public void setReportFormat(ReportFormat v) { this.reportFormat = v; }
}
