package com.pharmaease.backend.controller.superadmin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminDashboardController {
	@GetMapping("/dashboard")
	public ResponseEntity<?> superAdminDashboard(){
		return null;
	}

}
