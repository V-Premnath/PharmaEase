package com.pharmaease.backend.service.pharmacy;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pharmaease.backend.context.ContextHolder;
import com.pharmaease.backend.model.pharmacy.Medicine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MedicineScheduler {

    @Autowired
    private RedisMedicineService redisService;
    
    @Autowired
    private PharmacyService pharmacyService;
    
    private final Logger log = LoggerFactory.getLogger(MedicineScheduler.class);

    @Autowired
    private MedicineService medicineService;  // ✅ Directly autowire your MedicineService
    @Autowired
    private JdbcTemplate jdbcTemplate;

        


    @Scheduled(fixedRate = 10 * 60 * 1000) // Every 10 min
    public void batchFetchMedicines() {
        List<String> pharmacyList ;
        log.info("COntext before batch Fetching : "+ContextHolder.getCurrentDb());
        try
        { 
        	ContextHolder.clear();
        	log.info("Cleared Context");
        	ContextHolder.setCurrentDb("SuperAdminDB");
        	log.info("setSUper admin Db: "+ContextHolder.getCurrentDb());
        	pharmacyList = pharmacyService.getAllPharmacies();
        	log.info("Fetched list of pharmacies: "+pharmacyList);
        	
        
        for (String pharmacy : pharmacyList) {
            // ✅ Directly fetch from DB per pharmacy
        	
        	log.info("getting from each data source,currenlty"+ ContextHolder.getCurrentDb()+" changing to pharmacy  ");
        	
        	ContextHolder.clear();
        	ContextHolder.setCurrentDb(pharmacy);
            List<Medicine> medicines = medicineService.getAllPharmacyMedicines(pharmacy);

            List<Map<String, Object>> medicines1 = jdbcTemplate.queryForList("SELECT * FROM medicines");
            log.info("Medicines fetched via JDBC: {}", medicines1);
           
            // ✅ Save names into Redis
            redisService.saveMedicines(pharmacy, medicines);
            log.info("Context now : "+ContextHolder.getCurrentDb());
            log.info("Fetched Medicines: "+medicines);
            ContextHolder.clear();
        }
        log.info("✅ Batch refreshed at: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
        catch (Exception e) {
        	log.error(e.getMessage());
    }
        finally {
        	log.info("Finally executing : "+ContextHolder.getCurrentDb());
        	ContextHolder.clear();
        	}
        
    
        }
}
