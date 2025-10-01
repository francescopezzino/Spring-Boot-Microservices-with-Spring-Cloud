package com.company.productservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Long priceId;
    private Long productId;
    private Double originalPrice;
    private Double discountPrice;
}
