package com.gtpeksegglobalenterprise.pricing.services;

import com.gtpeksegglobalenterprise.pricing.dto.PriceRequestDto;
import com.gtpeksegglobalenterprise.pricing.dto.PriceResponseDto;
import com.gtpeksegglobalenterprise.pricing.models.PricingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private PricingService service;

    @BeforeEach
    void setUp() {
        service = new PricingService(new PricingConfig());
    }

    // ── price calculation ──────────────────────────────────────────────────────

    @Test
    void vipBookHolidayCampaignHighLoyaltyUs() {
        // VIP 20% capped at BOOK 40% → 20%, HOLIDAY +10%, loyalty +3% = 33% total
        // 100 * 0.67 = 67.00 + US tax 8% (5.36) = 72.36
        PriceResponseDto resp = service.calculate(
                req("C001", "VIP", "BOOK", 100, "US", "HOLIDAY", "JSON", "EMAIL", 200));
            assertEquals(72.36, resp.getFinalPrice(), 0.001);
            }

    @Test
    void employeeElectronics_categoryCapApplied() {
        // EMPLOYEE 30% but ELECTRONICS caps at 25%, no campaign, no loyalty = 25%
        // 200 * 0.75 = 150.00 + EU tax 20% (30.00) = 180.00
        PriceResponseDto resp = service.calculate(
                req("C002", "EMPLOYEE", "ELECTRONICS", 200, "EU", null, "PDF", "SMS", 0));
        assertEquals(180.0, resp.getFinalPrice(), 0.001);
    }

    @Test
    void regularClothingWinterSale_belowLoyaltyThreshold() {
        // REGULAR 5%, CLOTHING cap 30% → 5%, WINTER_SALE +8%, 50 pts (< 100) → 0% = 13%
        // 100 * 0.87 = 87.00 + UK tax 20% (17.40) = 104.40
        PriceResponseDto resp = service.calculate(
                req("C003", "REGULAR", "CLOTHING", 100, "UK", "WINTER_SALE", "CSV", "EMAIL", 50));
        assertEquals(104.4, resp.getFinalPrice(), 0.001);
    }

    @Test
    void vipClothing_loyaltyBonusNotApplied_belowThreshold() {
        // VIP 20%, CLOTHING cap 30% → 20%, no campaign, 99 pts (< 100) → 0% = 20%
        // 100 * 0.80 = 80.00 + CA tax 13% (10.40) = 90.40
        PriceResponseDto resp = service.calculate(
                req("C004", "VIP", "CLOTHING", 100, "CA", null, "JSON", "SMS", 99));
        assertEquals(90.4, resp.getFinalPrice(), 0.001);
    }

    @Test
    void loyaltyBonusApplied_exactlyAtThreshold() {
        // REGULAR 5%, BOOK cap 40% → 5%, no campaign, exactly 100 pts → +3% = 8%
        // 100 * 0.92 = 92.00 + US tax 8% (7.36) = 99.36
       PriceResponseDto resp = service.calculate(
                req("C005", "REGULAR", "BOOK", 100, "US", null, "CSV", "EMAIL", 100));
        assertEquals(99.36, resp.getFinalPrice(), 0.001);
    }

    @Test
    void nullCountry_defaultsToUsTax() {
        // REGULAR 5%, BOOK → 5%, no campaign, no loyalty, null country → US 8%
        // 100 * 0.95 = 95.00 + tax 7.60 = 102.60
        PriceResponseDto resp = service.calculate(
                req("C006", "REGULAR", "BOOK", 100, null, null, "PDF", "EMAIL", 0));
        assertEquals(102.6, resp.getFinalPrice(), 0.001);
    }

    @Test
    void unknownCountry_zeroPercentTax() {
        // EMPLOYEE 30%, BOOK cap 40% → 30%, no campaign, no loyalty = 30%; unknown country → 0%
        // 100 * 0.70 = 70.00
        PriceResponseDto resp = service.calculate(
                req("C007", "EMPLOYEE", "BOOK", 100, "XX", null, "JSON", "EMAIL", 0));
        assertEquals(70.0, resp.getFinalPrice(), 0.001);
    }

    @Test
    void totalDiscountCappedAtConfiguredMax() {
        // Custom config with 35% cap
        // EMPLOYEE 30%, BOOK → 30%, HOLIDAY +10%, 100 pts +5% = 45% → clamped to 35%
        // 100 * 0.65 = 65.00 + US tax 8% (5.20) = 70.20
        PricingConfig cfg = new PricingConfig();
        cfg.setMaxTotalDiscount(35.0);
        PricingService capped = new PricingService(cfg);
        PriceResponseDto resp = capped.calculate(
                req("C008", "EMPLOYEE", "BOOK", 100, "US", "HOLIDAY", "CSV", "SMS", 100));
        assertEquals(70.2, resp.getFinalPrice(), 0.001);
    }

    @Test
    void holidayCampaignAddsTenPercent() {
        // VIP 20%, BOOK → 20%, HOLIDAY +10%, no loyalty = 30%
        // 100 * 0.70 = 70.00 + US tax 8% (5.60) = 75.60
        PriceResponseDto resp = service.calculate(
                req("C009", "VIP", "BOOK", 100, "US", "HOLIDAY", "JSON", "EMAIL", 0));
        assertEquals(75.6, resp.getFinalPrice(), 0.001);
    }

    @Test
    void winterSaleCampaignAddsEightPercent() {
        // VIP 20%, BOOK → 20%, WINTER_SALE +8%, no loyalty = 28%
        // 100 * 0.72 = 72.00 + US tax 8% (5.76) = 77.76
        PriceResponseDto resp = service.calculate(
                req("C010", "VIP", "BOOK", 100, "US", "WINTER_SALE", "JSON", "EMAIL", 0));
        assertEquals(77.76, resp.getFinalPrice(), 0.001);
    }

    // ── validation ─────────────────────────────────────────────────────────────

    @Test
    void nullRequest_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(null));
    }

    @Test
    void blankCustomerId_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req(" ", "VIP", "BOOK", 100, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void blankCustomerType_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "", "BOOK", 100, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void blankProductCategory_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "", 100, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void zeroPriceThrows() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "BOOK", 0, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void negativePriceThrows() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "BOOK", -1, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void blankReportFormat_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "BOOK", 100, "US", null, "", "EMAIL", 0)));
    }

    @Test
    void blankNotificationType_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "BOOK", 100, "US", null, "JSON", "", 0)));
    }

    // ── unknown values ─────────────────────────────────────────────────────────

    @Test
    void unknownCustomerType_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "UNICORN", "BOOK", 100, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void unknownProductCategory_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "UNICORN", 100, "US", null, "JSON", "EMAIL", 0)));
    }

    @Test
    void unknownCampaign_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "BOOK", 100, "US", "UNICORN", "JSON", "EMAIL", 0)));
    }

    @Test
    void unknownNotificationType_throws() {
        assertThrows(RuntimeException.class, () -> service.calculate(
                req("C001", "VIP", "BOOK", 100, "US", null, "JSON", "CARRIER_PIGEON", 0)));
    }

    // ── helpers ────────────────────────────────────────────────────────────────

    private PriceRequestDto req(String customerId, String customerType, String category,
                                 double basePrice, String country, String campaign,
                                 String reportFormat, String notificationType, int loyaltyPoints) {
        PriceRequestDto r = new PriceRequestDto();
        r.setCustomerId(customerId);
        r.setCustomerType(customerType);
        r.setProductCategory(category);
        r.setBasePrice(basePrice);
        r.setCountry(country);
        r.setCampaign(campaign);
        r.setReportFormat(reportFormat);
        r.setNotificationType(notificationType);
        r.setLoyaltyPoints(loyaltyPoints);
        return r;
    }
    @Test
    void loyaltyBoundaryValues() {

        
        PricingConfig cfg = new PricingConfig();
        PricingService srv = new PricingService(cfg);

        assertEquals(102.60, srv.calculate(req("C", "REGULAR", "FOOD", 100, "US", null, "JSON", "EMAIL", 0)).getFinalPrice(), 0.001);
        
        assertEquals(102.60, srv.calculate(req("C", "REGULAR", "FOOD", 100, "US", null, "JSON", "EMAIL", 99)).getFinalPrice(), 0.001);

        assertEquals(99.36, srv.calculate(req("C", "REGULAR", "BOOK", 100, "US", null, "JSON", "EMAIL", 100)).getFinalPrice(), 0.001);
        
        assertEquals(99.36, srv.calculate(req("C", "REGULAR", "BOOK", 100, "US", null, "JSON", "EMAIL", 499)).getFinalPrice(), 0.001);

        assertEquals(95.04, srv.calculate(req("C", "REGULAR", "BOOK", 100, "US", null, "JSON", "EMAIL", 500)).getFinalPrice(), 0.001);
        
        assertEquals(95.04, srv.calculate(req("C", "REGULAR", "BOOK", 100, "US", null, "JSON", "EMAIL", 501)).getFinalPrice(), 0.001);
    }

    @Test
    void testCaseInsensitivity() {
        double price1 = service.calculate(req("C1", "vip", "book", 100, "us", "holiday", "json", "email", 0)).getFinalPrice();
        double price2 = service.calculate(req("C1", "VIP", "BOOK", 100, "US", "HOLIDAY", "JSON", "EMAIL", 0)).getFinalPrice();
        double price3 = service.calculate(req("C1", "Vip", "Book", 100, "Us", "Holiday", "Json", "Email", 0)).getFinalPrice();

        assertEquals(price1, price2);
        assertEquals(price2, price3);
    }

}
