package com.pharmaease.backend.service.superadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaease.backend.exception.UserNotFoundException;
import com.pharmaease.backend.model.superadmin.User;
import com.pharmaease.backend.repository.superadmin.UserRepo;

@Service
public class UserAuthenticationService {
	
	@Autowired
	private UserRepo ur;


	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);
	
	
	public User checkExistingUser(String email) {
		
		User user = ur.findFirstByUserEmail(email);
			if (user == null) {
				logger.info("UserNotFound in User authentication service line 21");
				return null;
				
			}
			else {
				return user;
				
			}
	}
public boolean checkExistingUserName(String name) throws UserNotFoundException{
		
		
		if (ur.findFirstByUsername(name) == null) return true;
		else return false;
	}
	public User getUserByUserName(String name) {
		logger.info("Name before : "+name);
		
		User user = ur.findFirstByUsername(name);
		if(user == null) {
			logger.info("UserNot found in get UserByUsername in user authentication Service line 45");
		}
		return user;
	}
	
	

}
