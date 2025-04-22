package com.pharmaease.backend.controller.superadmin;

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

import com.pharmaease.backend.model.superadmin.Notification;
import com.pharmaease.backend.service.pharmacy.PharmacyService;
import com.pharmaease.backend.service.superadmin.NotificationService;
import com.pharmaease.backend.service.superadmin.PharmacyCreationService;
import com.pharmaease.backend.service.superadmin.UserAuthenticationService;

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

	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@PostMapping("/superadmin")
	public ResponseEntity<?> getNotifications(HttpServletRequest request,HttpServletResponse response) 
	{
		String username = request.getHeader("username");
		if(userAuthservice.getUserByUserName(username) == null){
			logger.info("Something is wrong!!!!!");
			return ResponseEntity.noContent().build();
		}
		else {
			Long id = userAuthservice.getUserByUserName(username).getId();
			List<Notification> notList= notificationService.getNotificationsByReceiverId(id);

	        if (notList == null || notList.isEmpty()) {
	            return ResponseEntity.noContent().build(); // No notifications
	        }

	        return ResponseEntity.ok(notList);
			
		}

	}
	@GetMapping("/superadmin/approve-pharmacy")
	public ResponseEntity<?> approvePharmacy(@RequestParam("id") Long notificationId) 
	{
		
		try {
	        // Your logic to approve the pharmacy
	        // Example: pharmacyService.approvePharmacy(pharmacyId);
			Notification notif = notificationService.getNotificationBySenderId(notificationId);
			Long adminId = notif.getSenderId();

			logger.info("ADMIN  at NotificationController: "+adminId);
			String dbname =  ps.getPharamcyByAdminId(adminId).getDatabaseName();
			logger.info("DBNAME  at NotificationController: "+dbname);
			pcs.createNewPharmacy(dbname);
			
			//Updates Pharamcy status in Pharmacy table and links pharmacy id with  phamracy Admin User
			ps.updateStatus(ps.getPharamcyByAdminId(adminId).getId(),"APPROVED",adminId);
			
			notificationService.markAsRead(notif);
			
			
			
			
	        return ResponseEntity.ok("Pharmacy approved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error approving pharmacy: " + e.getMessage());
	    }
		
	}
	@GetMapping("/superadmin/disapprove-pharmacy")
	public ResponseEntity<?> disapprovePharmacy(@RequestParam("id") Long notificationId) {
	    try {
	        // Your logic to disapprove the pharmacy
	        // Example: pharmacyService.disapprovePharmacy(pharmacyId);
	    	Notification notif = notificationService.getNotificationBySenderId(notificationId);
	    	logger.info("Notification is being mark as read: "+notif);
	    	notificationService.markAsRead(notif);

	        return ResponseEntity.ok("Pharmacy disapproved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error disapproving pharmacy: " + e.getMessage());
	    }
	}

	

}
