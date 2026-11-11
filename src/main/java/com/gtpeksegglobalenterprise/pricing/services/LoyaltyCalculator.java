package com.gtpeksegglobalenterprise.pricing.services;

public class LoyaltyCalculator {
    public static double calculateBonus(int points) {
        if (points >= 500) return 7.0;
        if (points >= 100) return 3.0;
        return 0.0;
    }
}