package com.pharmaease.backend.service.superadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmaease.backend.exception.UserNotFoundException;
import com.pharmaease.backend.model.superadmin.User;
import com.pharmaease.backend.repository.superadmin.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo ur;


	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public User enrollNewUser(User user) {
			
			if (user != null) {
				user = ur.saveAndFlush(user);}
			else {
				throw new UserNotFoundException("No user to enroll in UserAuthenticationservice");
			}
			
			return user;
		}
	@Transactional
	public void deleteUserByUserEmail(String email) {
		
		logger.info("Deleting User with Email : "+email);
		
		String mail = getUserByUserEmail(email).getUserEmail();
		//Double Checking From Db for Existing User
		if(mail!=null)
			ur.deleteByUserEmail(email);
		else
			throw new UserNotFoundException("No user with given email to delete in DB");
		return;
	}
	public User getUserByUserEmail(String email) {
		
		User user = ur.findFirstByUserEmail(email);
		logger.info(email+" id : "+user);
		return user;
	}
	
}
