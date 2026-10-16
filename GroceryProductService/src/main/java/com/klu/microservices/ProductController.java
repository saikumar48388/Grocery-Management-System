package com.klu.microservices;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public List<Product> getProducts() {
        return List.of(
            new Product(101, "Rice 1 kg",
                new BigDecimal("60.00")),
            new Product(102, "Sugar 1 kg",
                new BigDecimal("45.00")),
            new Product(103, "Milk 500 ml",
                new BigDecimal("30.00"))
        );
    }
}