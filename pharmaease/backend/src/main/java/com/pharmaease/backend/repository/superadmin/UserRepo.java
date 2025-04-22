package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findFirstByUserEmail(String email);

	User findFirstByUsername(String name);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.pharmacyId = :pid WHERE u.id = :adminId ")
	
	void updateUserPharmacyId(@Param ("pid") Long pid,@Param("adminId") Long adminId) ;

	void deleteByUserEmail(String string); 

}
