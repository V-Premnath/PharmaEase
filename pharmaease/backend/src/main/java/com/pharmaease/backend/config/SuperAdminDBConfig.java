package com.pharmaease.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.pharmaease.backend.repository.superadmin"
)
public class SuperAdminDBConfig {
    // No need to define beans — Spring Boot handles the default DataSource, EntityManager, and TxManager
}
