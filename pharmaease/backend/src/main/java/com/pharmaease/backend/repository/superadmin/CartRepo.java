package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {}