package com.company.pricingservice;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    List<Price> priceList = new ArrayList<>();

    public PriceController() {
        populatePriceList();
    }

    @GetMapping("/price/{productId}")
    public Price getPriceDetails(@PathVariable Long productId) {
        Price price = getPriceInfo(productId);

        return new Price(price.getPriceId(), price.getProductId(), price.getOriginalPrice(), price.getDiscountPrice());
    }

    private Price getPriceInfo(Long productId) {
        for (Price price : priceList) {
            if (price.getProductId().equals(productId)) {
                return price;
            }
        }
        return null;
    }

    private void populatePriceList() {
        priceList.clear();
        priceList.add(new Price(201L, 101L, 1999,999));
        priceList.add(new Price(202L, 102L, 199,19));
        priceList.add(new Price(203L, 103L, 1222,600));
    }
}
