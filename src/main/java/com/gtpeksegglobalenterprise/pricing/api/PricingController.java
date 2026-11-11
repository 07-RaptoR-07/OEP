package com.gtpeksegglobalenterprise.pricing.api;

import com.gtpeksegglobalenterprise.pricing.dto.PriceRequestDto;
import com.gtpeksegglobalenterprise.pricing.dto.PriceResponseDto;
import com.gtpeksegglobalenterprise.pricing.services.PricingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
class PricingController {

    private final PricingService pricingService;

    PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping("/calculate")
    public PriceResponseDto calculate(@RequestBody PriceRequestDto request) {
        return pricingService.calculate(request);
    }
}
