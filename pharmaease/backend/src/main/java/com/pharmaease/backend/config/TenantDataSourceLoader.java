package com.pharmaease.backend.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import com.pharmaease.backend.context.ContextHolder;

@Component
public class TenantDataSourceLoader implements CommandLineRunner {

    private final MultiTenantRoutingDataSource routingDataSource;

    private static final Logger log = LoggerFactory.getLogger(TenantDataSourceLoader.class);

    public TenantDataSourceLoader(DataSource dataSource) {
        this.routingDataSource = (MultiTenantRoutingDataSource) dataSource;
    }

    @Override
    public void run(String... args) {
        // Set current tenant so repo works
        ContextHolder.setCurrentDb("SuperAdminDB");

        try (Connection connection = routingDataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT pharmacy_database_name FROM pharmacies");
             ResultSet rs = stmt.executeQuery()) {

            Map<Object, Object> tenantDataSources = new HashMap<>();
            while (rs.next()) {
            	log.info("result statement : ",rs.getObject("pharmacy_database_name"));
                String dbName = rs.getString("pharmacy_database_name");
                DataSource ds = DataSourceBuilder.create()
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .url("jdbc:mysql://localhost:3306/" + dbName)
                        .username("root")
                        .password("Prem@123")
                        .build();
                tenantDataSources.put(dbName, ds);
                initializeSchema(ds);

            }

            // Add to existing map
            routingDataSource.setTargetDataSources(tenantDataSources);
            routingDataSource.afterPropertiesSet();

            System.out.println("✅ Loaded tenants: " + routingDataSource.getResolvedDataSources());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ContextHolder.clear();
        }
    }
    private void initializeSchema(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.pharmaease.backend.model.pharmacy"); // Path to your @Entity classes
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProps = new HashMap<>();
        jpaProps.put("hibernate.hbm2ddl.auto", "update"); // Or "create" for fresh schema
        jpaProps.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        emf.setJpaPropertyMap(jpaProps);
        emf.afterPropertiesSet(); // ⚠️ This is what actually triggers Hibernate table creation
    }

}

