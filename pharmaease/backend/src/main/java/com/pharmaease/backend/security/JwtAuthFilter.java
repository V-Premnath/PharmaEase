package com.pharmaease.backend.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pharmaease.backend.context.ContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Current DB before any filtering As of in jwt auth filter : [ "+ContextHolder.getCurrentDb()+" ]");

        // 🛑 Skip JWT filter for OAuth2 and auth routes
        if (path.startsWith("/") || path.startsWith("/auth/") || path.startsWith("/oauth2/") || path.startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("Missing or malformed Authorization header: {}", authHeader);
            log.info("Incoming request path: {}", path);

//            filterChain.doFilter(request, response); // Proceed unauthenticated
            ContextHolder.clear();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"JWT token invalid or expired.\"}");
            return;
        }
        log.info("Incoming request path: {}", path);


        final String token = authHeader.substring(7); // Strip "Bearer "
        String username = null;
        String role = null;

        try {
            username = jwtService.extractUsername(token);
            role = jwtService.extractRole(token);
            if((request.getHeader("dbname")!=null) && (request.getHeader("role").equals("DRUGGIST") || request.getHeader("role").equals("PHARMACY_ADMIN"))) {
             	log.info(request.getHeader("dbname")+" before setting in context holder");
            	ContextHolder.setCurrentDb(request.getHeader("dbname"));
            	log.info(request.getHeader("dbname")+" set in context holder");
            }
            else {
            	log.info("SuperAdminDb before settting in context holder");
            	ContextHolder.setCurrentDb("SuperAdminDB");
            	log.info("SuperAdminDb set in context holder");
            }
            log.info("Authenticated user: {} with role: {}", username, role);
        } catch (Exception e) {
            log.error("JWT extraction failed: {}", e.getMessage());
            ContextHolder.clear();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"JWT token invalid or expired.\"}");

            // Or redirect to error page in frontend
            // response.sendRedirect("https://pharmaease-1.onrender.com/error");

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

        try {
            filterChain.doFilter(request, response); // 🔁 Continue the filter chain
        } finally {
            ContextHolder.clear(); // ✅ Clear AFTER the whole request is done
        }
    }
}

