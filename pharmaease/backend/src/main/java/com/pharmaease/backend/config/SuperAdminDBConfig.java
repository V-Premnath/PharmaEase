package com.pharmaease.backend.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.pharmaease.backend.repository.superadmin",
    entityManagerFactoryRef = "superAdminEntityManagerFactory",
    transactionManagerRef = "superAdminTransactionManager"
)
public class SuperAdminDBConfig {

    @Value("${spring.datasource.superadmin.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.superadmin.username}")
    private String username;

    @Value("${spring.datasource.superadmin.password}")
    private String password;

    @Value("${spring.datasource.superadmin.driver-class-name}")
    private String driverClassName;

    @Primary
    @Bean(name = "superAdminDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(jdbcUrl)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build();
    }

    @Primary
    @Bean(name = "superAdminEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean superAdminEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.pharmaease.backend.model.superadmin");
        em.setPersistenceUnitName("superAdminPU");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean(name = "superAdminTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}