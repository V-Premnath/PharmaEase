package com.pharmaease.backend.service.pharmacy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaease.backend.model.pharmacy.Medicine;

@Service
public class BatchMedicineService {

    @Autowired
    private MedicineService medicineService;

    private Map<String, List<Medicine>> pharmacyMedicineMap = new ConcurrentHashMap<>();

    public void refreshAllPharmacyMedicines(List<String> pharmacyNames) {
        for (String pharmacyName : pharmacyNames) {
            List<Medicine> medicines = medicineService.getAllPharmacyMedicines(pharmacyName);
            pharmacyMedicineMap.put(pharmacyName, medicines);
        }
    }

    public List<Medicine> getMedicinesForPharmacy(String pharmacyName) {
        return pharmacyMedicineMap.getOrDefault(pharmacyName, new ArrayList<>());
    }

    public Map<String, List<Medicine>> getAllPharmacyMedicines() {
        return pharmacyMedicineMap;
    }
}
