package com.pharmaease.backend.repository.superadmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.superadmin.Role;



@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String rolename);
	}