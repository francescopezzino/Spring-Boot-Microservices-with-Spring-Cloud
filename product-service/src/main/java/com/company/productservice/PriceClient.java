package com.company.productservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRICING-SERVICE")
public interface PriceClient {

    @GetMapping("/price/{productId}")
    Price getPriceDetails(@PathVariable("productId") Long productId);

}
