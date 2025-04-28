package com.pharmaease.backend.controller.superadmin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaease.backend.context.ContextHolder;
import com.pharmaease.backend.model.superadmin.Notification;
import com.pharmaease.backend.model.superadmin.Pharmacy;
import com.pharmaease.backend.service.pharmacy.PharmacyService;
import com.pharmaease.backend.service.superadmin.NotificationService;
import com.pharmaease.backend.service.superadmin.PharmacyCreationService;
import com.pharmaease.backend.service.superadmin.UserAuthenticationService;
import com.pharmaease.backend.service.superadmin.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	@Autowired 
	private UserAuthenticationService userAuthservice;
	@Autowired
	private PharmacyService ps;
	@Autowired 
	private PharmacyCreationService pcs;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@PostMapping("/")
	public ResponseEntity<?> getNotifications(HttpServletRequest request,HttpServletResponse response) 
	{
		String username = request.getHeader("username");
		List<Notification> notList = new ArrayList<>();
		String temp = ContextHolder.getCurrentDb();
		logger.info("Notifications loading .... current DB: "+temp);
		try {
			
			ContextHolder.setCurrentDb("SuperAdminDB");
			
			if(userAuthservice.getUserByUserName(username) == null){
				logger.info("Something is wrong!!!!!");
				return ResponseEntity.noContent().build();
			}
			else {
				logger.info("Getting notifications for the user: "+username);
				Long id = userAuthservice.getUserByUserName(username).getId();
				notList= notificationService.getNotificationsByReceiverId(id);
				
		        if (notList == null || notList.isEmpty()) {
		            return ResponseEntity.noContent().build(); // No notifications
		        }
	
		        return ResponseEntity.ok(notList);
				
			}
		
	}
		catch(Exception e) {
			logger.info("Here");
		}
		finally {
			ContextHolder.clear();
			ContextHolder.setCurrentDb(temp);
		}
		return ResponseEntity.ok(notList);

	}
	@GetMapping("/superadmin/approve-pharmacy")
	public ResponseEntity<?> approvePharmacy(@RequestParam("id") Long NotificationId,@RequestParam("senderId") Long SenderId) 
	{
			logger.info("Notification approval request param: "+ SenderId);
		try {
	        // Your logic to approve the pharmacy
	        // Example: pharmacyService.approvePharmacy(pharmacyId);
			
			Long adminId = SenderId;

			logger.info("ADMIN  at NotificationController: "+adminId);
			Pharmacy pharm = ps.getPharamcyByAdminId(adminId);
			String dbname =  pharm.getDatabaseName();
			logger.info("DBNAME  at NotificationController: "+dbname);
			pcs.createNewPharmacy(dbname);
			
			//Updates Pharamcy status in Pharmacy table and links pharmacy id with  phamracy Admin User
			ps.updatePharmacyStatus(ps.getPharamcyByAdminId(adminId).getId(),"APPROVED");
			userService.updateUserPharmacy(adminId,pharm.getName());
			notificationService.markAsRead(notificationService.getNotificationBySenderId(SenderId));
			
			
			
			
	        return ResponseEntity.ok("Pharmacy approved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error approving pharmacy: " + e.getMessage());
	    }
		
	}
	@GetMapping("/superadmin/disapprove-pharmacy")
	public ResponseEntity<?> disapproveDruggist(@RequestParam("id") Long notificationId,@RequestParam("senderId") Long senderId) {
	    try {
	        // Your logic to disapprove the pharmacy
	        // Example: pharmacyService.disapprovePharmacy(pharmacyId);
	    	Notification notif = notificationService.getNotificationById(notificationId);
	    	logger.info("Notification is being mark as read: "+notif);
	    	notificationService.markAsRead(notif);

	        return ResponseEntity.ok("Pharmacy disapproved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error disapproving pharmacy: " + e.getMessage());
	    }
	}

	@GetMapping("/pharmacyadmin/approve-druggist")
	public ResponseEntity<?> approveDruggist(@RequestParam("id") Long notificationId,@RequestParam("senderId") Long senderId) 
	{
		String temp = ContextHolder.getCurrentDb();
		try {
			
			ContextHolder.setCurrentDb("SuperAdminDB");
			
			
			userService.updateUserPharmacy(senderId,"DRUGGIST");//passing druggist as aflag to rerieve pharmacy name from user password field #PATCH
			logger.info("PharmAdmin approval  at NotificationController: ");
			
			Notification notif = notificationService.getNotificationById(notificationId);
			notificationService.markAsRead(notif);
						
	        return ResponseEntity.ok("DRUGGIST approved successfully");
	    
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error approving druggist: " + e.getMessage());
	    }
		finally {
			ContextHolder.clear();
			ContextHolder.setCurrentDb(temp);
		}
		
	}
	@GetMapping("/pharmacyadmin/disapprove-druggist")
	public ResponseEntity<?> disapprovePharmacy(@RequestParam("id") Long notificationId,@RequestParam("senderId") Long senderId) {
	    try {
	    	ContextHolder.setCurrentDb("SuperAdminDB");
			
	    	Notification notif = notificationService.getNotificationById(notificationId);
	    	logger.info("Notification is being mark as read: "+notif);
	    	notificationService.markAsRead(notif);

	        return ResponseEntity.ok("DRUGGIST disapproved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error disapproving Druggist: " + e.getMessage());
	    }
	}
	

}
