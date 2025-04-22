package com.pharmaease.backend.repository.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.pharmacy.Medicine;

import java.util.List;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Long> {
    
    // Find medicine by name (case-insensitive search)
    List<Medicine> findByNameIgnoreCase(String name);

    // Find medicines by vendor ID
    List<Medicine> findByPurchaseId(Long purchaseId);

    // Find medicines that are low in stock (threshold can be set dynamically)
    List<Medicine> findByStockLessThan(int threshold);

    // Find expired medicines
    List<Medicine> findByExpDateBefore(java.util.Date currentDate);
}
