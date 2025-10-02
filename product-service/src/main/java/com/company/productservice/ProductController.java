package com.company.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    List<ProductInfo> productList = new ArrayList<>();

    //create an instance of a WebClient
    public WebClient webClient = WebClient.create();


    @GetMapping("/product/details/{productId}")
    public Mono<Product> getProductDetails(@PathVariable Long productId) {

        System.out.println("1");
        // Get Name and Desc from product-service
        Mono<ProductInfo> productInfo = Mono.just(getProductInfo(productId));

        // Get Price from pricing-service
        // introducing WebClient to make the API calls
        Mono<Price> price = webClient.get().uri("http://localhost:8002/price/{productId}", productId).retrieve().bodyToMono(Price.class);
        
        // Get Stock Avail from invntory-serivice
        Mono<Inventory> inventory = webClient.get().uri("http://localhost:8003/inventory/{productId}", productId).retrieve().bodyToMono(Inventory.class);
        System.out.println("2");

        //return Mono.zip(productInfo, price, inventory).map(this::buildProduct);
        // convert the method reference to a lambda:
        return Mono.zip(productInfo, price, inventory).map(tuple ->
                new Product(tuple.getT1().getProductId(),
                        tuple.getT1().getProductName(),
                        tuple.getT1().getProductDesc(),
                        tuple.getT2().getDiscountPrice(),
                        tuple.getT3().getInStock()));
    }

    /*
    Can get
    public Product buildProduct(Tuple3<ProductInfo, Price, Inventory> tuple) {
        return new Product(tuple.getT1().getProductId(), tuple.getT1().getProductName(), tuple.getT1().getProductDesc(), tuple.getT2().getDiscountPrice(), tuple.getT3().getInStock());
    }
    */

    private ProductInfo getProductInfo(Long productId) {
        populateProductList();
        for (ProductInfo productInfo : productList) {
            if (productInfo.getProductId().equals(productId)) {
                return productInfo;
            }
        }
        return null;
    }

    private void populateProductList() {
        productList.clear();
        productList.add(new ProductInfo(101L, "iPhone", "iPhone is damn expensive"));
        productList.add(new ProductInfo(102L, "Book", "Book is great"));
        productList.add(new ProductInfo(103L, "Washing machine", "Washing machine is necessary"));
    }
}
