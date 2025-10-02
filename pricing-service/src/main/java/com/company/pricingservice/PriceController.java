package com.company.pricingservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    @Autowired
    private RestTemplate restTemplate;

    List<Price> priceList = new ArrayList<>();

    public PriceController() {
        populatePriceList();
    }

    @GetMapping("/price/{productId}")
    public Mono<Price> getPriceDetails(@PathVariable Long productId) {
        Mono<Price> price = Mono.just(getPriceInfo(productId));

        // Simulate it takes 10 sec to process, to show the asychronous reactive response do not wait
        // for the thread to complete
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Get Exchange Value
        //Integer exgVal = restTemplate.getForObject("http://localhost:8004/currexg/from/USD/to/INR", ExgVal.class).getExgVal();

        //return new Price(price.getPriceId(), price.getProductId(), price.getOriginalPrice(), Math.multiplyExact(exgVal, price.getDiscountPrice()));
        return price;
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
