package com.jpmc.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private String type;
    private BigDecimal price;
    private Integer quantity;

    public static Product createProduct(String type, BigDecimal price, Integer quantity) {
        return new Product(type, price, quantity);
    }

    public String salesReportString() {
        return "-----Product:" + type + " Number of sales:"+ quantity + " Total Value:" + (price.multiply(BigDecimal.valueOf(quantity))) + "------";
    }
}

