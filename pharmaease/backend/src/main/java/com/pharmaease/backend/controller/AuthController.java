package com.pharmaease.backend.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaease.backend.model.superadmin.Role;
import com.pharmaease.backend.model.superadmin.User;
import com.pharmaease.backend.repository.superadmin.RoleRepo;
import com.pharmaease.backend.service.pharmacy.PharmacyService;
import com.pharmaease.backend.service.superadmin.PharmacyCreationService;
import com.pharmaease.backend.service.superadmin.UserAuthenticationService;
import com.pharmaease.backend.service.superadmin.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserAuthenticationService user_auth_service; 
    @Autowired
    private PharmacyCreationService pharm_create_service;
    @Autowired
    private UserService user_service;
    @Autowired
    private RoleRepo rolerepo;
    @Autowired
    private PharmacyService pharmService;
    
    //============================================
    
//    Need to update and validate from DB in real time, also need to log and manage sessions
    
//    Here I have an idea, while in frontend entering useremail, or google OAuth, just by logging with email,
//    should map with their role,by an api route maybe(rendering maybe)
    //============================================

    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    
    @PostMapping("/google-login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
    	logger.info("Redirecting user to Google OAuth login page.");
    }
    @PostMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestBody Map<String,String> requestBody, HttpServletResponse response){
    	boolean goodUsername = user_auth_service.checkExistingUserName(requestBody.get("username"));
    	if (goodUsername)
    		return ResponseEntity.accepted().build();
    	else
    		return ResponseEntity.badRequest().build();
    	
    }
    @PostMapping("/check-pharmacy-name")
    public ResponseEntity<?> checkPharmacyname(@RequestBody Map<String,String> requestBody, HttpServletResponse response){
    	boolean goodPharmacyname = pharmService.getPharmacyByName(requestBody.get("pharmacyName"))!=null;
    	if (goodPharmacyname)
    		return ResponseEntity.accepted().build();
    	else
    		return ResponseEntity.badRequest().build();
    	
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> requestBody,HttpServletResponse response) {
    	
    	logger.info("Inside the signup"); 
    	String username = requestBody.get("username");
        String role1 = requestBody.get("role");
        String email = requestBody.get("email");
        String pharmacyName = requestBody.get("pharmacyName");
        String password = requestBody.get("password");
        String address = requestBody.get("address");    	
        logger.info("Role from request: "+role1+" username : "+username+" Email: "+email+" pName  : "+pharmacyName+" Password : "+password+" address : "+address);
//    	Long pharmacyId = null;
//    	public User(String username, String password, Role role, Pharmacy pharmacyId,String email) {
//    	User(username, role, pharmacyId, email); 
    	Role role = rolerepo.findByName(role1);
//    	Role role = rolerepo.findByName(role1).orElseThrow(() -> new RuntimeException("Invalid role: req role:"+role1 )); // or whatever role
    	
    	if (email.equals("")) {
    		return ResponseEntity.badRequest().build();
    	}
    	if (role1.equals("DRUGGIST"))////       There is no coupling from pharmacy to druggsit as of now to temporarity solve the issue strong pharmacyName in password field
    	{
    		password=pharmacyName;
    	}
    	
    	User user = new User(username,password,role,null,email);
    	
    	user = user_service.enrollNewUser(user);
    	
    	logger.info("Auth Controller line 82 : RolE:"+role.getName());
    	if (role.getName().equals("PHARMACY_ADMIN")) {
    		logger.info("Auth Controller line 84 : RolE:"+role.getName());
    		pharm_create_service.enrollPharmacy(pharmacyName,address,user.getId());
    	}
    	return ResponseEntity.ok().build();
    	
    }
    
}
