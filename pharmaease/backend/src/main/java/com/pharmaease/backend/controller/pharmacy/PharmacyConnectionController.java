package com.pharmaease.backend.controller.pharmacy;


import com.pharmaease.backend.dto.pharmacy.PharmacyConnectionRequest;
import com.pharmaease.backend.model.pharmacy.Medicine;
import com.pharmaease.backend.repository.pharmacy.MedicineRepo;
import com.pharmaease.backend.security.JwtAuthFilter;
import com.pharmaease.backend.service.pharmacy.DynamicPharmacyDBService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyConnectionController {
	@Autowired
	private MedicineRepo medicineRepo;


    private static final Logger log = LoggerFactory.getLogger(PharmacyConnectionController.class);

    private final DynamicPharmacyDBService dynamicPharmacyDBService;

    public PharmacyConnectionController(DynamicPharmacyDBService dynamicPharmacyDBService) {
        this.dynamicPharmacyDBService = dynamicPharmacyDBService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testPharmacyConnection(HttpServletRequest request) {
    	boolean success = true;
    	
    	

        if (success) {
        	System.out.println(request	);
            return ResponseEntity.ok("Connection successful!");
        } else {
            return ResponseEntity.status(401).body("Connection failed: Invalid DB name or password.");
        }
    }
    @GetMapping("/customer/medicines")
    public ResponseEntity<Map<String, Object>> getAllMedicines() {
        List<Medicine> medicines = medicineRepo.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", medicines);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/customer/medicine")
    public ResponseEntity<?> getMedicine(HttpServletRequest request) {
    	
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            log.info("ID  in pharm controller : "+id);
            Optional<Medicine> medicineOpt = medicineRepo.findById(id);

            if (medicineOpt.isPresent()) {
                return ResponseEntity.ok(medicineOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine not found");
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        } 
    	}
    }
