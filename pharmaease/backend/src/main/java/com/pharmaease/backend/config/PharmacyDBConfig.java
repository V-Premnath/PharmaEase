package com.pharmaease.backend.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.pharmaease.backend.repository.pharmacy"
)
public class PharmacyDBConfig {

    public DataSource createDataSource(String dbName, String password) {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true")
                .username("root")//Need to change 
                .password(password)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.pharmaease.backend.model.pharmacy");
        em.setPersistenceUnitName("dynamicPharmacyPU");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "none"); // NEVER auto-update schema for testing!
        properties.put("hibernate.show_sql", false);
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet(); // ensure initialization

        return em;
    }
}
