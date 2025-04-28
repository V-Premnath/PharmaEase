package com.pharmaease.backend.service.pharmacy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaease.backend.model.superadmin.Pharmacy;
import com.pharmaease.backend.repository.superadmin.PharmacyRepo;

@Service
public class PharmacyService {

	@Autowired
	private PharmacyRepo pr;
	

	private static final Logger logger = LoggerFactory.getLogger(PharmacyService.class);
	
	public Pharmacy getPharamcyByAdminId(Long id) {
		Pharmacy pharm =pr.findByAdminId(id);
		logger.info("Optional return object in pharmacy service for id : "+id+"   "+pr.findByAdminId(id));
		logger.info("Pharmacy service: dbname : "+pharm.getDatabaseName());
		
		return pharm ;
	}

	public void updatePharmacyStatus(Long pid,String string) {
		// 
		//Updating Pharmacy Status to "APPROVED"
		if (string.equals("APPROVED")){
			pr.updatePharmacyStatus(pid, true);
		}
		else {
			logger.info("Soething other than 'APPROVED' ");
		}
	}

	public void deletePharmacyByPharmacyId(Long pid) {
		// 
		pr.deleteById(pid);
	}
	
	public List<String> getAllPharmacies(){
		List<String> pharmList = pr.findAllPharmacyDBNames();
		return pharmList;
		
	}

	public Pharmacy getPharmacyByName(String pharmacyName) {
		// 
		return pr.findByName(pharmacyName);
		
	}

	public Pharmacy getPharmacyByDBName(String pharmacyDBName) {
		return pr.findByDatabaseName(pharmacyDBName);
	}

	public Pharmacy getPharmacyById(Long pharmacyId) {
//		 
		return pr.findById(pharmacyId).orElse(new Pharmacy());
		
	}
	
}
