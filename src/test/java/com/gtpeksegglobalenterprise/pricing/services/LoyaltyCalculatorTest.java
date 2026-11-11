package com.gtpeksegglobalenterprise.pricing.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoyaltyCalculatorTest {

    @Test
    void testLoyaltyTiers() {
        assertEquals(0.0, LoyaltyCalculator.calculateBonus(0));
        assertEquals(0.0, LoyaltyCalculator.calculateBonus(99));
        assertEquals(3.0, LoyaltyCalculator.calculateBonus(100));
        assertEquals(3.0, LoyaltyCalculator.calculateBonus(499));
        assertEquals(7.0, LoyaltyCalculator.calculateBonus(500));
        assertEquals(7.0, LoyaltyCalculator.calculateBonus(1000));
    }
}