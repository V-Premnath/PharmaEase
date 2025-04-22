package com.pharmaease.backend.controller.superadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaease.backend.service.pharmacy.PharmacyService;
import com.pharmaease.backend.service.superadmin.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private 
	PharmacyService ps;
	@Autowired
	private
	UserService us;
	
	@GetMapping("/revoke-approval-request")
	public void revokeUserApproval(@RequestParam String email) {
		
		
		Long adminid = us.getUserByUserEmail(email).getId();
		Long pid = ps.getPharamcyByAdminId(adminid).getId();
		ps.deletePharmacyByPharmacyId(pid);
		us.deleteUserByUserEmail(email);
		
		
		
	}

}
