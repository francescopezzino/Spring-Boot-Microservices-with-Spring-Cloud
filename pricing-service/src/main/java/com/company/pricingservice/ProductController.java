package com.company.pricingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    List<Price> priceList = new ArrayList<>();

    @GetMapping("/price/{productId}")
    public Price getPriceDetails(@PathVariable Long productId) {
        Price price = getPriceInfo(productId);

        return price;
    }

    private Price getPriceInfo(Long productId) {
        populatePriceList();
        for (Price price : priceList) {
            if (price.getProductId().equals(productId)) {
                return price;
            }
        }
        return null;
    }

    private void populatePriceList() {
        priceList.add(new Price(201L, 101L, 1999.0,999.0));
        priceList.add(new Price(202L, 102L, 199.0,19.0));
        priceList.add(new Price(203L, 101L, 1222.0,600.0));
    }
}
