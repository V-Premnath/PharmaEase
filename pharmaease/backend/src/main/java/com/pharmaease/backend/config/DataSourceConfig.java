package com.pharmaease.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
    
    public DataSourceConfig() { }

    @Bean
    public DataSource dataSource() {
        MultiTenantRoutingDataSource multiTenantDataSource = new MultiTenantRoutingDataSource();
        
        // Set default tenant to SuperAdminDB
        DataSource superAdminDataSource = createSuperAdminDataSource();
        multiTenantDataSource.setDefaultTargetDataSource(superAdminDataSource);
        
        // Set only superadmin for now
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("SuperAdminDB", superAdminDataSource);
        multiTenantDataSource.setTargetDataSources(dataSources);

        multiTenantDataSource.afterPropertiesSet();

        return multiTenantDataSource;
    }


    private DataSource createSuperAdminDataSource() {
        log.info("Creating DataSource for >>>> SUPERADMIN DB");
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/SuperAdminDB")
                .username("root")
                .password("Prem@123")
                .build();
    }

}
