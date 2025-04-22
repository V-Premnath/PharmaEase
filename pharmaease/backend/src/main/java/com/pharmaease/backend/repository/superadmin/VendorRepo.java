package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VendorRepo extends JpaRepository<Vendor, Long> {}