package com.pharmaease.backend.repository.pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.pharmacy.Sale;



@Repository
public interface SaleRepo extends JpaRepository<Sale, Long> {}