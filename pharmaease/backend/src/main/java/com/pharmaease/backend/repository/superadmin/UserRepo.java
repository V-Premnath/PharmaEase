package com.pharmaease.backend.repository.superadmin;
import com.pharmaease.backend.model.superadmin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
