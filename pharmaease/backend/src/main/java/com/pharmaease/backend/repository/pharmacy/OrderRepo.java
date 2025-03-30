package com.pharmaease.backend.repository.pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmaease.backend.model.pharmacy.Order;



@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {}