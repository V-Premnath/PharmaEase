package com.pharmaease.backend.repository.superadmin;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharmaease.backend.model.superadmin.Pharmacy;



@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, Long> {

	Pharmacy findByName(String pharmacyName);
	
	Pharmacy findByAdminId(Long id);
	@Modifying
    @Transactional
    @Query("UPDATE Pharmacy p SET p.accessGranted = :status WHERE p.id = :id")
    int updatePharmacyStatus(@Param("id") Long id, @Param("status") boolean status);

	@Query("SELECT p.databaseName FROM Pharmacy p")
	List<String> findAllPharmacyDBNames();

	@Query("SELECT p.name FROM Pharmacy p")
	List<String> findAllPharmacyNames();

	Pharmacy findByDatabaseName(String pharmacyDBName);
}
