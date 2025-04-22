package com.pharmaease.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 🛑 Skip JWT filter for OAuth2 and auth routes
        if (path.startsWith("/auth/") || path.startsWith("/oauth2/") || path.startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("Missing or malformed Authorization header: {}", authHeader);
            filterChain.doFilter(request, response); // Proceed unauthenticated
        
            return;
        }
        log.info("Incoming request path: {}", path);


        final String token = authHeader.substring(7); // Strip "Bearer "
        String username = null;
        String role = null;

        try {
            username = jwtService.extractUsername(token);
            role = jwtService.extractRole(token);
            log.info("Authenticated user: {} with role: {}", username, role);
        } catch (Exception e) {
            log.error("JWT extraction failed: {}", e.getMessage());
            filterChain.doFilter(request, response); // Proceed unauthenticated
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Valid token found for user: {}", username);

            if (jwtService.isTokenValid(token, username)) {
                List<SimpleGrantedAuthority> authorities =
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                log.info("Setting authentication for user: {}", username);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } else {
            log.info("No username extracted or already authenticated.");
        }

        filterChain.doFilter(request, response);
    }
}

