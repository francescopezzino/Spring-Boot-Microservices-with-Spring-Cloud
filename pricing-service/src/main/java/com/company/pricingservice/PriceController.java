package com.company.pricingservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    @Autowired
    private RestTemplate restTemplate;

    List<Price> priceList = new ArrayList<>();

    @GetMapping("/price/{productId}")
    public Price getPriceDetails(@PathVariable Long productId) {
        Price price = getPriceInfo(productId);

        // Get Exchange Value
        Integer exgVal = restTemplate.getForObject("http://localhost:8004/currexg/from/USD/to/INR", ExgVal.class).getExgVal();

        return new Price(price.getPriceId(), price.getProductId(), price.getOriginalPrice(), Math.multiplyExact(exgVal, price.getDiscountPrice()));
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
        priceList.clear();.
        priceList.add(new Price(201L, 101L, 1999,999));
        priceList.add(new Price(202L, 102L, 199,19));
        priceList.add(new Price(203L, 101L, 1222,600));
    }
}
