package com.pharmaease.backend.repository.superadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.superadmin.Cart;




@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);
	
}