package com.pharmaease.backend.repository.superadmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.superadmin.Pharmacy;
import com.pharmaease.backend.model.superadmin.User;

import jakarta.transaction.Transactional;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findFirstByUserEmail(String email);

	User findFirstByUsername(String name);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.pharmacyId = :pharm WHERE u.id = :adminId ")
	void updateUserPharmacy(@Param ("pharm") Pharmacy pharm ,@Param("adminId") Long adminId) ;

	void deleteByUserEmail(String string); 

}
