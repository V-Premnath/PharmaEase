package com.pharmaease.backend.service.pharmacy;


import com.pharmaease.backend.config.PharmacyDBConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

@Service
public class DynamicPharmacyDBService {

    private final PharmacyDBConfig pharmacyDBConfig;

    public DynamicPharmacyDBService(PharmacyDBConfig pharmacyDBConfig) {
        this.pharmacyDBConfig = pharmacyDBConfig;
    }
    

    public boolean testConnection(String dbName, String password) {
        try {
            EntityManagerFactory emf = pharmacyDBConfig
                    .createEntityManagerFactory(pharmacyDBConfig.createDataSource(dbName, password))
                    .getObject();

            if (emf == null) {
                return false;
            }

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult(); // just validates the connection
            em.getTransaction().commit();
            em.close();
            emf.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

