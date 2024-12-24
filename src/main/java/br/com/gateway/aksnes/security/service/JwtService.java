package br.com.gateway.aksnes.security.service;

import br.com.gateway.aksnes.exception.ApiSecurityException;
import br.com.gateway.aksnes.security.persistence.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    public String generateToken(UserEntity user) {
        if (!hasLength(user.getEmail())) throw new IllegalArgumentException("Username cannot be null or empty");
        var roles = user.getUserRoles().stream().map(role -> role.getRole().name()).toList();

        try {
            return Jwts.builder()
                    .subject(user.getEmail())
                    .claim("userId", user.getUserId())
                    .claim("roles", roles)
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

    public Jwt<?, Claims> parseJwt(CharSequence charSequence) throws ApiSecurityException {
        try {
            return Jwts.parser().verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(charSequence);
        } catch (ExpiredJwtException ex) {
            throw new ApiSecurityException(HttpStatus.UNAUTHORIZED, "Token expired");
        } catch (MalformedJwtException ex) {
            throw new ApiSecurityException(HttpStatus.BAD_REQUEST, "Malformed JWT token");
        } catch (SignatureException ex) {
            throw new ApiSecurityException(HttpStatus.UNAUTHORIZED, "Invalid token signature");
        } catch (JwtException ex) {
            throw new ApiSecurityException(HttpStatus.UNAUTHORIZED, "Error parsing JWT token");
        }
    }
}
