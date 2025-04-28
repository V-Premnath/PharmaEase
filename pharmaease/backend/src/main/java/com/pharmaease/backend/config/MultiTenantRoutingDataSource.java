package com.pharmaease.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.pharmaease.backend.context.ContextHolder;

public class MultiTenantRoutingDataSource extends AbstractRoutingDataSource {

	private final Logger log = LoggerFactory.getLogger(MultiTenantRoutingDataSource.class);
	@Override
    protected Object determineCurrentLookupKey() {
    	log.info("Getting current Db : "+ContextHolder.getCurrentDb()+" Target Sources : "+this.getResolvedDataSources());
        return ContextHolder.getCurrentDb();
    }
    
   
}
