package br.com.gateway.aksnes.security.filter;

import br.com.gateway.aksnes.security.service.JwtService;
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

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull  FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");

        if(isInvalidToken(authorization)) {
            log.error("Invalid JWT or not present");
            filterChain.doFilter(request, response);
            return;
        }

        var charSequence = authorization.substring(7);

        var jwt = jwtService.parseJwt(charSequence);
        var username = jwt.getPayload().getSubject();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }


    private boolean isInvalidToken(final String authorization) {
        return (authorization == null || authorization.equalsIgnoreCase("null")) || !authorization.startsWith("Bearer ");
    }
}
