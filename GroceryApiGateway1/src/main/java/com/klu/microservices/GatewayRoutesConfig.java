package com.klu.microservices;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        return builder.routes()

                .route("auth-service", route -> route
                        .path("/api/auth", "/api/auth/**")
                        .uri("lb://GROCERYAUTHSERVICE"))

                .route("product-service", route -> route
                        .path("/api/products", "/api/products/**")
                        .uri("lb://GROCERYPRODUCTSERVICE"))

                .route("order-service", route -> route
                        .path("/api/orders", "/api/orders/**")
                        .uri("lb://GROCERYORDERSERVICE"))

                .build();
    }
}