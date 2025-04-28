package com.pharmaease.backend.service.pharmacy;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmaease.backend.model.pharmacy.Medicine;
import com.pharmaease.backend.repository.pharmacy.MedicineRepo;

@Service
@Transactional
public class MedicineService {

    @Autowired
    private MedicineRepo medrepo;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final Logger logger = LoggerFactory.getLogger(MedicineService.class);
    
    // Add your DEFAULT database name here (VERY IMPORTANT)
    private static final String DEFAULT_DATABASE = "SuperAdminDB"; 

    public List<Medicine> getAllPharmacyMedicines(String pharmacyName) {
        List<Medicine> medList = new ArrayList<>();
        logger.info("Fetching all medicines for pharmacy: {}", pharmacyName);
        
        try {
            logger.info("Switching database to: {}", pharmacyName);
            entityManager.createNativeQuery("USE " + pharmacyName).executeUpdate();
            
            medList = medrepo.findAll();
            logger.info("Fetched Medicines: {}", medList);
        } catch (Exception e) {
            logger.error("Error while fetching medicines: ", e);
        } finally {
            // Switch back to default database
            try {
                logger.info("Switching back to default database: {}", DEFAULT_DATABASE);
                entityManager.createNativeQuery("USE " + DEFAULT_DATABASE).executeUpdate();
            } catch (Exception e) {
                logger.error("Error while switching back to default DB: ", e);
            }
        }
        
        return medList;
    }

    public Medicine getMedicineById(String pharmacyDBName, Long id) {
        Medicine med = new Medicine();
        logger.info("Fetching medicine by ID {} for pharmacy: {}", id, pharmacyDBName);
        
        try {
            logger.info("Switching database to: {}", pharmacyDBName);
            entityManager.createNativeQuery("USE " + pharmacyDBName).executeUpdate();
            
            med = medrepo.findById(id).orElse(new Medicine());
            logger.info("Fetched Medicine: {}", med);
        } catch (Exception e) {
            logger.error("Error while fetching medicine by id: ", e);
        } finally {
            // Switch back to default database
            try {
                logger.info("Switching back to default database: {}", DEFAULT_DATABASE);
                entityManager.createNativeQuery("USE " + DEFAULT_DATABASE).executeUpdate();
            } catch (Exception e) {
                logger.error("Error while switching back to default DB: ", e);
            }
        }
        
        return med;
    }
}
