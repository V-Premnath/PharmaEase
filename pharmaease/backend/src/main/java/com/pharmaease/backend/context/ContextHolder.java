package com.pharmaease.backend.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContextHolder {

    private static final Logger log = LoggerFactory.getLogger(ContextHolder.class);

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setCurrentDb(String dbName) {
    	log.info("Set db name in context successful : "+dbName);
        CONTEXT.set(dbName);
    }

    public static String getCurrentDb() {
    	log.info("get db name from context successful : "+CONTEXT.get());
        return CONTEXT.get();
    }

    public static void clear() {
    	log.info("clearing context");
        CONTEXT.remove();
    }

}
