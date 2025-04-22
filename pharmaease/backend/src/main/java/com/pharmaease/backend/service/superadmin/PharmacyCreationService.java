package com.pharmaease.backend.service.superadmin;


import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaease.backend.model.superadmin.Notification;
import com.pharmaease.backend.model.superadmin.Pharmacy;
import com.pharmaease.backend.repository.custom.DatabaseManagerRepository;
import com.pharmaease.backend.repository.superadmin.NotificationRepo;
import com.pharmaease.backend.repository.superadmin.PharmacyRepo;

@Service
public class PharmacyCreationService {
	
	@Autowired
    private DatabaseManagerRepository dbManagerRepo;
	@Autowired 
	private PharmacyRepo pharmacyRepo;
	@Autowired
	private NotificationRepo notify_repo;
	

	private static final Logger logger = LoggerFactory.getLogger(PharmacyCreationService.class);

    public void createNewPharmacy(String pharmacyName) {
        dbManagerRepo.createDatabase(pharmacyName);
    }



	public boolean enrollPharmacy(String pharmacyName,String address,Long userid) {
		logger.info("Inside the enroll Pharmacy method in service layer");
		// TODO Auto-generated method stub
		if (pharmacyRepo.findByName(pharmacyName) != null) {
            throw new IllegalArgumentException("Pharmacy already exists");
        }
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setAccessGranted(false);
		pharmacy.setAddress(address);
		pharmacy.setAdminId(userid);
		pharmacy.setDatabaseName(pharmacyName+"_DB_"+userid);
		pharmacy.setName(pharmacyName);       

        pharmacy = pharmacyRepo.saveAndFlush(pharmacy);
        if(pharmacy == null) {
        	return false;}
        else {

    		logger.info("ICreating Notification");
        	
        	Notification not = new Notification();
        	not.setMessage(pharmacyName+"Requesting approval " );
        	not.setSenderId(userid);
        	not.setReceiverId(Integer.toUnsignedLong(2));//hard code superadmin userId
        	not.setTime(LocalDateTime.now());
        	
        	not = notify_repo.saveAndFlush(not);
        	if (not == null) {
        		logger.info("Notification creation unsuccessful");
        		return false;
        	}
        	return true;}
		
	}

}
