package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PrescriptionRepo extends JpaRepository<Prescription, Long> {}