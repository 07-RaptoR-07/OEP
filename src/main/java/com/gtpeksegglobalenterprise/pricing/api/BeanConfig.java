package com.gtpeksegglobalenterprise.pricing.api;

import com.gtpeksegglobalenterprise.pricing.models.PricingConfig;
import com.gtpeksegglobalenterprise.pricing.services.PricingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfig {

    @Bean
    PricingService pricingService() {
        return new PricingService(new PricingConfig());
    }
}
