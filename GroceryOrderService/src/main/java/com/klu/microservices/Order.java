package com.klu.microservices;

import java.math.BigDecimal;

public class Order {

    private final int orderId;
    private final int productId;
    private final int quantity;
    private final BigDecimal totalAmount;
    private final String status;

    public Order(int orderId, int productId, int quantity,
                 BigDecimal totalAmount, String status) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
}