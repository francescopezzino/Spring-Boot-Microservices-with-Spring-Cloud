package com.company.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    List<ProductInfo> productList = new ArrayList<>();

    @Autowired
    private PriceClient priceClient;

    @Autowired
    private InventoryClient inventoryClient;

    @GetMapping("/product/details/{productId}")
    public Product getProductDetails(@PathVariable Long productId) {

        // Get Name and Desc from product-service
        ProductInfo productInfo = getProductInfo(productId);

        // Get Price from pricing-service using Feigh client
        Price price = priceClient.getPriceDetails(productId);

        // Get Stock Avail from invntory-serivice using feign client
        Inventory inventory = inventoryClient.getInventoryDetails(productId);

        return new Product(productInfo.getProductId(), productInfo.getProductName(), productInfo.getProductDesc(), price.getDiscountPrice(), inventory.getInStock());
    }

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
