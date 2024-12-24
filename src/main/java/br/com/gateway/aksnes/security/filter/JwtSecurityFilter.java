package br.com.gateway.aksnes.security.filter;

import br.com.gateway.aksnes.exception.ApiSecurityException;
import br.com.gateway.aksnes.security.dto.ErrorResponseDTO;
import br.com.gateway.aksnes.security.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull  FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        final String requestPath = request.getRequestURI();

        if (requestPath.startsWith("/api/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(isInvalidToken(authorization)) {
            log.error("Invalid JWT or not present");
            filterChain.doFilter(request, response);
            return;
        }

        var charSequence = authorization.substring(7);
        Jwt<?, Claims> jwt;

        try {
            jwt = jwtService.parseJwt(charSequence);
            var username = jwt.getPayload().getSubject();

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
                filterChain.doFilter(request, response);
                return;
            }
        } catch (ApiSecurityException ex) {
            log.error("Error extracting claims from JWT token: {} - Token: {}", ex.getMessage(), charSequence);
            var responseBodyAsString = marshalPojoToJsonString(new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage()));

            response.setStatus(ex.getHttpStatus().value());
            response.getWriter().write(responseBodyAsString);
            response.getWriter().flush();
            return;
        }


        filterChain.doFilter(request, response);
    }


    private boolean isInvalidToken(final String authorization) {
        return (authorization == null || authorization.equalsIgnoreCase("null")) || !authorization.startsWith("Bearer ");
    }

    private String marshalPojoToJsonString(ErrorResponseDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException ex) {
            log.error("Error marshalling POJO to JSON: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
