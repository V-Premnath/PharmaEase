package com.pharmaease.backend.dto.pharmacy;

public class PharmacyConnectionRequest {
    private String dbName;
    private String password;

    // Getters and Setters
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
