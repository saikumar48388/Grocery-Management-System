package com.klu.microservices;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;

@Service
public class JwtService {

    private final RSAKey rsaKey;
    private final NimbusJwtEncoder encoder;

    public JwtService() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        rsaKey = new RSAKey.Builder(
                (RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();

        ImmutableJWKSet<SecurityContext> keySource =
                new ImmutableJWKSet<>(new JWKSet(rsaKey));

        encoder = new NimbusJwtEncoder(keySource);
    }

    public String generateToken(String username) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:8086")
                .subject(username)
                .audience(List.of("grocery-api"))
                .issuedAt(now)
                .notBefore(now)
                .expiresAt(now.plusSeconds(900))
                .id(UUID.randomUUID().toString())
                .build();

        JwsHeader header = JwsHeader
                .with(SignatureAlgorithm.RS256)
                .keyId(rsaKey.getKeyID())
                .build();

        return encoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    public Map<String, Object> publicKeys() {
        // Only the public key is shared.
        return new JWKSet(rsaKey.toPublicJWK()).toJSONObject();
    }
}