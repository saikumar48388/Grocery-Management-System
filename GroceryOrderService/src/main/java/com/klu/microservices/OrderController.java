package com.klu.microservices;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping
    public List<Order> getOrders() {
        return List.of(
            new Order(1001, 101, 2,
                new BigDecimal("120.00"), "PLACED"),
            new Order(1002, 102, 3,
                new BigDecimal("135.00"), "PACKED"),
            new Order(1003, 103, 1,
                new BigDecimal("30.00"), "DELIVERED")
        );
    }
}