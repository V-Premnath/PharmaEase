package com.pharmaease.backend.repository.custom;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharmaease.backend.repository.DatabaseManagerRepositoryInterface;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class DatabaseManagerRepository implements DatabaseManagerRepositoryInterface {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	@Transactional
    public void createDatabase(String dbName) {
        try {
            // Sanitize dbName to avoid SQL injection
            if (!dbName.matches("^[a-zA-Z0-9_]+$")) {
                throw new IllegalArgumentException("Invalid database name");
            }

            String query = "CREATE DATABASE IF NOT EXISTS " + dbName;
            entityManager.createNativeQuery(query).executeUpdate();
            System.out.println("Database " + dbName + " created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating database: " + e.getMessage());
            // Optionally rethrow or handle the exception
        }
    }

}
