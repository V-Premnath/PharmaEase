package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.Role;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String rolename);
	}