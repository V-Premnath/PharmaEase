package com.pharmaease.backend.service.superadmin;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmaease.backend.exception.UserNotFoundException;
import com.pharmaease.backend.model.superadmin.Notification;
import com.pharmaease.backend.model.superadmin.Pharmacy;
import com.pharmaease.backend.model.superadmin.User;
import com.pharmaease.backend.repository.superadmin.UserRepo;
import com.pharmaease.backend.service.pharmacy.PharmacyService;

@Service
public class UserService {
	
	@Autowired
	private UserRepo ur;
	@Autowired
	private NotificationService notifServ;
	@Autowired
	private PharmacyService pharmServ;


	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public User enrollNewUser(User user) {
		logger.info("Enrolling user : "+user.toString());
			
			if (user != null) {
				user = ur.saveAndFlush(user);
				if(user.getRole().getName().equals("DRUGGIST")) {

					logger.info("After Enrolling user : "+user.toString());
					logger.info("Inside druggist user creation");
					
					Pharmacy pharm = pharmServ.getPharmacyByName(user.getPassword());
					Notification not = new Notification();
		        	not.setMessage("Druggist,"+user.getUsername()+" Requesting approval "+"for pharmacy:"+pharm.getName() );
		        	not.setSenderId(user.getId());
		        	not.setReceiverId(pharm.getAdminId());
		        	not.setTime(LocalDateTime.now());
		        	logger.info("Notification while enrlling user : "+not.toString());
		        	notifServ.createNotification(not);
					
				}
				
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
	
	public void updateUserPharmacy(Long id,String pName) {
		if(pName.equals("DRUGGIST")){
			pName = getUserById(id).getPassword(); ///user pharmacy name is stored in password field #PATCH
			ur.updateUserPharmacy(pharmServ.getPharmacyByName(pName), id);
			return;
		}
		ur.updateUserPharmacy(pharmServ.getPharmacyByName(pName), id);
		return;
	}
	public User getUserById(Long senderId) {
		return ur.findById(senderId).orElse(new User());
		
	}
	public User getUserByUsername(String name) {
		return ur.findFirstByUsername(name);
		
	}
}
