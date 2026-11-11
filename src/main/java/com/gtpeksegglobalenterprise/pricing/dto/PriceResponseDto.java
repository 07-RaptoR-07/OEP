package com.gtpeksegglobalenterprise.pricing.dto;

/**
 * Outgoing price-calculation response.
 *
 * <p>
 * Field names map directly to the JSON wire format. Do not rename, remove,
 * or change the type of any field — doing so breaks the API contract with
 * callers without any compile-time warning.
 */
public class PriceResponseDto {

    private double finalPrice;

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double v) {
        this.finalPrice = v;
    }
}
