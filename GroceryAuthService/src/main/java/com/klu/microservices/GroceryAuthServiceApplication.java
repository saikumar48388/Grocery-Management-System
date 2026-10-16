package com.klu.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GroceryAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            GroceryAuthServiceApplication.class,
            args
        );
    }
}