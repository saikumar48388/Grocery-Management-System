package com.klu.microservices;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
            // This gateway uses Bearer tokens, not cookie-based login.
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .logout(logout -> logout.disable())
            .securityContextRepository(
                NoOpServerSecurityContextRepository.getInstance()
            )
            .requestCache(cache -> cache.requestCache(
                NoOpServerRequestCache.getInstance()
            ))
            .authorizeExchange(auth -> auth
                .pathMatchers(HttpMethod.POST, "/api/auth/login")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/auth/jwks")
                .permitAll()
                .pathMatchers(
                    "/api/products",
                    "/api/products/**",
                    "/api/orders",
                    "/api/orders/**"
                )
                .authenticated()
                .anyExchange().denyAll()
            )
            .oauth2ResourceServer(oauth -> oauth
                .jwt(Customizer.withDefaults())
            )
            .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder decoder =
            NimbusReactiveJwtDecoder
                .withJwkSetUri("http://localhost:8086/api/auth/jwks")
                .jwsAlgorithm(SignatureAlgorithm.RS256)
                .build();

        JwtClaimValidator<List<String>> audienceValidator =
            new JwtClaimValidator<>(
                "aud",
                audience -> audience != null
                    && audience.contains("grocery-api")
            );

        decoder.setJwtValidator(
            new DelegatingOAuth2TokenValidator<Jwt>(
                JwtValidators.createDefaultWithIssuer(
                    "http://localhost:8086"
                ),
                audienceValidator
            )
        );

        return decoder;
    }
}