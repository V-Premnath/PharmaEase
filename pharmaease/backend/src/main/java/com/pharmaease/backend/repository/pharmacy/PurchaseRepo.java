package com.pharmaease.backend.repository.pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.pharmacy.Purchase;



@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {}