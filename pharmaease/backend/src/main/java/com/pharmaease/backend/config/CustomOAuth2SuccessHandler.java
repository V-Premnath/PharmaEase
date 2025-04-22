package com.pharmaease.backend.config;

import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pharmaease.backend.controller.AuthController;
import com.pharmaease.backend.model.superadmin.User;
import com.pharmaease.backend.security.JwtService;
import com.pharmaease.backend.service.superadmin.UserAuthenticationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private JwtService jwtService;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private UserAuthenticationService userAuth ;	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication)
	        throws IOException, ServletException {
		
		
        logger.info(">>> Inside CustomOAuth2SuccessHandler");

	    OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
	    OAuth2User user = token.getPrincipal();	

	    String email = user.getAttribute("email");
	    User retrieved_user = userAuth.checkExistingUser(email);
	    logger.info("==== > > > line 44 ++++ OAuth2User token.getPRincipal :   "+user.toString()+"Email: "+email+"Check Existing : "+retrieved_user);
	    
	    if (retrieved_user == null) {
	    	response.sendRedirect("http://localhost:5173/signup?email="+URLEncoder.encode(email, "UTF-8"));
	    	return;
	    }
	    
	    String role = retrieved_user.getRole().getName();  // or fetch from DB
	    String username = retrieved_user.getUsername();
//	    Map<String, Object> claims = Map.of("role", role);
	    logger.info("===========================================MAPING ROLES AND USERNAME?EMAIL role : "+role);
	    String jwt = jwtService.generateToken(email, role);

        logger.info("Generated JWT for user {}: {}", email, jwt);
	    // ✅ Set response content type
        response.setHeader("Authorization", jwt); 
        response.setHeader("role", role); 
        response.setHeader("username", username);
        if (( role.equals( "PHARMACY_ADMIN" ) || role.equals( "DRUGGIST" ) ) && ( retrieved_user.getPharmacy()==null) ) {
        	String redirectUrl = "http://localhost:5173/waiting-approval?email="+email;
            response.sendRedirect(redirectUrl);
            return;
        }
        String redirectUrl = "http://localhost:5173/oauth2-redirect?token=" + jwt+"&role="+role+"&username="+username;
        response.sendRedirect(redirectUrl);
        return;
	}

}
