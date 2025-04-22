package com.pharmaease.backend.service.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaease.backend.model.superadmin.Pharmacy;
import com.pharmaease.backend.repository.superadmin.PharmacyRepo;
import com.pharmaease.backend.repository.superadmin.UserRepo;

@Service
public class PharmacyService {

	@Autowired
	private PharmacyRepo pr;
	@Autowired
	private UserRepo ur;
	

	private static final Logger logger = LoggerFactory.getLogger(PharmacyService.class);
	
	public Pharmacy getPharamcyByAdminId(Long id) {
		Pharmacy pharm =pr.findByAdminId(id);
		logger.info("Optional return object in pharmacy service for id : "+id+"   "+pr.findByAdminId(id));
		
		
		
		logger.info("Pharmacy servicce: dbname :"+pharm.getDatabaseName());
		
		return pharm ;
	}

	public void updateStatus(Long pid,String string,Long adminId) {
		// TODO Auto-generated method stubp
		//Updating Pharmacy Status to "APPROVED"
		pr.updatePharmacyStatus(pid, true);
		//Updating the user and linking him wth the pharmacy the user is associated with
		ur.updateUserPharmacyId(pid,adminId);
	}

	public void deletePharmacyByPharmacyId(Long pid) {
		// TODO Auto-generated method stub
		pr.deleteById(pid);
	}
	
}
