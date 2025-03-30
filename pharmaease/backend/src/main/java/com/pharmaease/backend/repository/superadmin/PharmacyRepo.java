package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, Long> {

}
