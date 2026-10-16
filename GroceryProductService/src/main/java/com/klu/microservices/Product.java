package com.klu.microservices;

import java.math.BigDecimal;

public class Product {

    private final int productId;
    private final String productName;
    private final BigDecimal price;

    public Product(int productId, String productName,
                   BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}