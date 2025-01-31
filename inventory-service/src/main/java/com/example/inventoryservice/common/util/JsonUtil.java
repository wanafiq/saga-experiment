package com.example.inventoryservice.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static String toJson(Object payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(payload);
        } catch (Exception e) {
            log.error("Failed to serialize payload: {}", e.getMessage());
            throw new InternalServerErrorException();
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Failed to deserialize payload: {} to class {}", e.getMessage(), clazz.getName());
            throw new InternalServerErrorException();
        }
    }
}
