package com.pharmaease.backend.service.pharmacy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaease.backend.model.pharmacy.Medicine;

@Service
public class RedisMedicineService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    ObjectMapper mapper;

    private static final String PREFIX = "PHARMACY_MEDICINES:";

    // Save medicines list to Redis
    public void saveMedicines(String pharmacyName, List<Medicine> medicines) {
        String key = PREFIX + pharmacyName;
        String value = serializeMedicines(medicines);
        redisTemplate.opsForValue().set(key, value);
    }

    // Get medicines list from Redis
    public List<Medicine> getMedicines(String pharmacyName) {
        String key = PREFIX + pharmacyName;
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return new ArrayList<>();
        }
        return deserializeMedicines(value);
    }

    private String serializeMedicines(List<Medicine> medicines) {
        try {
//            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(medicines);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Medicine> deserializeMedicines(String json) {
        try {
//            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference <List<Medicine>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

