package com.coursera.demomicroservices.currencyexchangeservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Model Object
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExgVal {

    private Long id;
    private Currencies from;
    private Currencies to;
    private Integer exgVal;
}
