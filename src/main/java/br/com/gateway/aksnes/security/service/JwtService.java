package br.com.gateway.aksnes.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.time.Instant.now;
import static org.springframework.util.StringUtils.hasLength;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret.issuer}")
    private String issuer;

    @Value("${jwt.expiration.minutes}")
    private long expirationMinutes;

    private final Clock clock;

    private final SecretKey secretKey;

    public JwtService(Clock clock, @Value("${jwt.secret.key}") String secret) {
        this.clock = clock;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        if (!hasLength(username)) throw new IllegalArgumentException("Username cannot be null or empty");

        try {
            return Jwts.builder()
                    .subject(username)
                    .issuer(issuer)
                    .issuedAt(Date.from(now(clock)))
                    .expiration(Date.from(now(clock).plus(expirationMinutes, ChronoUnit.MINUTES)))
                    .signWith(secretKey)
                    .compact();
        } catch (JwtException ex) {
            log.error("Error generating JWT token: {}", ex.getMessage());
            throw new RuntimeException("Failed to generate JWT token", ex);
        }
    }

    public Jwt<?, Claims> parseJwt(CharSequence charSequence) {
        try {
            return Jwts.parser().verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(charSequence);
        } catch (JwtException ex) {
            log.error("Error extracting claims from JWT token: {}", ex.getMessage());
            throw new RuntimeException("Failed to extract claims from JWT token", ex);
        }
    }
}
