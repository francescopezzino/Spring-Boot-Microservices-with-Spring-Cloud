package com.company.pricingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Long priceId;
    private Long productId;
    private Integer originalPrice;
    private Integer discountPrice;
}
