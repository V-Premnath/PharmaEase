package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {}