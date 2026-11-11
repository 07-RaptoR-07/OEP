package com.gtpeksegglobalenterprise.pricing.services;

import com.gtpeksegglobalenterprise.pricing.dto.PriceRequestDto;
import com.gtpeksegglobalenterprise.pricing.dto.PriceResponseDto;
import com.gtpeksegglobalenterprise.pricing.models.*;
import com.gtpeksegglobalenterprise.pricing.services.notifications.*;
import com.gtpeksegglobalenterprise.pricing.services.reports.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Calculates the final price for a product purchase.
 */
public class PricingService {

    private final PricingConfig config;
    private final Map<NotificationType, NotificationChannel> notificationChannels;
    private final Map<ReportFormat, ReportGenerator> reportGenerators;
    public PricingService(PricingConfig config) {
        this.config = config;

        this.notificationChannels = new EnumMap<>(NotificationType.class);
        this.notificationChannels.put(NotificationType.EMAIL, new EmailNotificationChannel());
        this.notificationChannels.put(NotificationType.SMS, new SmsNotificationChannel());

        this.reportGenerators = new EnumMap<>(ReportFormat.class);
        this.reportGenerators.put(ReportFormat.JSON, new JsonReportGenerator());
        this.reportGenerators.put(ReportFormat.PDF, new PdfReportGenerator());
        this.reportGenerators.put(ReportFormat.CSV, new CsvReportGenerator());
        this.reportGenerators.put(ReportFormat.XML, new XmlReportGenerator());
        this.reportGenerators.put(ReportFormat.MARKDOWN, new MarkdownReportGenerator());
    }
    // ── public API ─────────────────────────────────────────────────────────────

    /**
     * Calculates the final price for the given request and returns it.
     *
     * <p>This method is the sole entry point called by the web layer. Its
     * signature must not change. You are free — and expected — to refactor
     * everything inside it and in the private methods it delegates to, as long
     * as it still accepts a {@link PriceRequestDto} and returns a
     * {@link PriceResponseDto} with the correct {@code finalPrice}.
     *
     * @param request the incoming price-calculation request; must not be null
     * @return the calculated price response
     * @throws RuntimeException if the request is invalid or contains an
     *                          unrecognised customer type, category, campaign,
     *                          report format, or notification type
     */
    public PriceResponseDto calculate(PriceRequestDto request) {
        validateBaseRequest(request);

        Customer customer = buildCustomer(request);
        Product product = buildProduct(request);
        Order order = buildOrder(request);

        double customerDiscount = customer.getType().getBaseDiscount();
        double cappedDiscount = Math.min(customerDiscount, product.getCategory().getMaxDiscount());

        double bonuses = 0.0;
        if (customer.getType().isEligibleForBonuses()) {
            bonuses += order.getCampaigns().stream().mapToDouble(Campaign::getBonus).sum();
            bonuses += LoyaltyCalculator.calculateBonus(customer.getLoyaltyPoints());
    }

        double totalDiscount = Math.min(cappedDiscount + bonuses, config.getMaxTotalDiscount());

        double discountedPrice = product.getBasePrice() * (1.0 - totalDiscount / 100.0);
        double taxRate = Country.getSafeTaxRate(customer.getCountryCode());
        double taxAmount = discountedPrice * taxRate / 100.0;
        double finalPrice = Math.round((discountedPrice + taxAmount) * 100.0) / 100.0;

        for (NotificationType channelType : customer.getNotificationChannels()) {
            notifyCustomer(channelType, customer, product, order, totalDiscount, finalPrice);
        }
        notifyManagers(customer, totalDiscount, finalPrice);
        
        generateReport(customer, product, order, totalDiscount, taxRate, finalPrice);

        PriceResponseDto response = new PriceResponseDto();
        response.setFinalPrice(finalPrice);
        return response;
    }

    // ── validation ─────────────────────────────────────────────────────────────

    private void validateBaseRequest(PriceRequestDto req) {
        if (req == null) throw new RuntimeException("request must not be null");
        if (req.getCustomerId() == null || req.getCustomerId().isBlank()) throw new RuntimeException("customerId is required");
        if (req.getBasePrice() <= 0) throw new RuntimeException("basePrice must be positive");
    }


    private Customer buildCustomer(PriceRequestDto req) {
        Customer c = new Customer();
        c.setCustomerId(req.getCustomerId());
        c.setType(CustomerType.fromString(req.getCustomerType()));
        c.setCountryCode(req.getCountry());
        c.setNotificationChannels(List.of(NotificationType.fromString(req.getNotificationType())));
        c.setLoyaltyPoints(req.getLoyaltyPoints());
        return c;
    }

    private Product buildProduct(PriceRequestDto req) {
        Product p = new Product();
        p.setCategory(ProductCategory.fromString(req.getProductCategory()));
        p.setBasePrice(req.getBasePrice());
        return p;
    }

    private Order buildOrder(PriceRequestDto req) {
        Order o = new Order();
        o.setReportFormat(ReportFormat.fromString(req.getReportFormat()));
        List<Campaign> campaigns = new ArrayList<>();
        Campaign camp = Campaign.fromString(req.getCampaign());
        if (camp != null) campaigns.add(camp);
        o.setCampaigns(campaigns);
        return o;
    }

   private void notifyCustomer(NotificationType channelType, Customer customer, Product product, Order order, double discount, double finalPrice) {
        NotificationChannel channel = notificationChannels.get(channelType);
        if (channel != null) {
            channel.notify(customer, product, order, discount, finalPrice);
        }
    }

    private void notifyManagers(Customer customer, double discount, double finalPrice) {
        if (finalPrice > config.getLargeTransactionThreshold()) {
            System.out.println("[MANAGER ALERT] Large transaction detected.");
        }
    }

    private void generateReport(Customer customer, Product product, Order order, double discount, double taxRate, double finalPrice) {
        ReportFormat fmt = order.getReportFormat();
        ReportGenerator generator = reportGenerators.get(fmt);
        if (generator != null) {
            generator.generate(customer, product, order, discount, taxRate, finalPrice);
        }
    }
}