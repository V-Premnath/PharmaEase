package com.pharmaease.backend.repository.superadmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.superadmin.Vendor;



@Repository
public interface VendorRepo extends JpaRepository<Vendor, Long> {}