package com.pharmaease.backend.config;

import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.pharmaease.backend.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${FRONTEND_BASE_URL}")
    private String FRONTEND_BASE_URL;
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	@Autowired
	private CustomOAuth2SuccessHandler customHandler;

    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        logger.info("Configuring SecurityFilterChain with CORS and CSRF disabled");
        
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Enable CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**", "/auth/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2.successHandler(customHandler)).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(FRONTEND_BASE_URL));  // Frontend URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type","token", "X-Requested-With","auth","role","username","dbname"));
        configuration.setAllowCredentials(true);  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
}




//http:
//Authorization: Bearer <jwt_token>
//JWT should include 
//{
//	  "sub": "premnath",
//	  "role": "ADMIN",
//	  "pharmacyDb": "pharmacy_42"
//	}
