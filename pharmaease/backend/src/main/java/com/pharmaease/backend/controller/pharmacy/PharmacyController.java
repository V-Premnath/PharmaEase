package com.pharmaease.backend.controller.pharmacy;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaease.backend.model.pharmacy.Medicine;
import com.pharmaease.backend.service.pharmacy.MedicineService;
import com.pharmaease.backend.service.pharmacy.PharmacyService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/pharmacy")
@NoArgsConstructor
public class PharmacyController {
	private static final Logger log = LoggerFactory.getLogger(PharmacyController.class);

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PharmacyService pharmService;
   
    // Route to fetch all medicines of a specific pharmacy
    @GetMapping("/{pharmacyDBName}/medicines")
    public ResponseEntity<Map<String, Object>> getAllMedicines(@PathVariable String pharmacyDBName) {
        log.info("Getting medicines for pharmacy: {}", pharmacyDBName);


        Map<String, Object> response = new HashMap<>();
        if (pharmService.getPharmacyByDBName(pharmacyDBName) == null) {
        	log.info("request for : "+pharmacyDBName+" => Db : "+pharmService.getPharmacyByDBName(pharmacyDBName));
        	response.put("status", "failure");
        	return ResponseEntity.ok(response);
        }
        
        List<Medicine> medicines = medicineService.getAllPharmacyMedicines(pharmacyDBName);

        response.put("status", "success");
        response.put("data", medicines);

        return ResponseEntity.ok(response);
    }

    // Route to fetch a specific medicine by ID from a pharmacy
    @GetMapping("/{pharmacyName}/medicine/{id}")
    public ResponseEntity<?> getMedicineById( @PathVariable String pharmacyName, HttpServletRequest request,@PathVariable Long id) {
        
    	try {
           
            log.info("Fetching medicine with ID {} from pharmacy {}", id, pharmacyName);
            Medicine med = medicineService.getMedicineById(pharmacyName, id);
            return new ResponseEntity<Medicine>(med,HttpStatusCode.valueOf(200));
                    

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        }
    }
    @GetMapping("/pharmacy-id/{pharmacyId}/medicine/{id}")
    public ResponseEntity<?> getCartMedicineById( @PathVariable Long pharmacyId, HttpServletRequest request,@PathVariable Long id) {
        
    	try {
           
            log.info("Fetching medicine with ID {} from pharmacy {}", id, pharmacyId);
            String pharmacyDBName = pharmService.getPharmacyById(pharmacyId).getDatabaseName();
            Medicine med = medicineService.getMedicineById(pharmacyDBName, id);
            return new ResponseEntity<Medicine>(med,HttpStatusCode.valueOf(200));
                    

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        }
    }
    
    @GetMapping("/load-all")
    public ResponseEntity<?> loadPharmacies(){
    	
    	List<String> pList = pharmService.getAllPharmacies();
    	
    	return ResponseEntity.ok(pList);
    }
}