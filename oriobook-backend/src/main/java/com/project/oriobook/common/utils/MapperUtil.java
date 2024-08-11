package com.project.oriobook.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertMapToJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T convertMapToObject(Map<String, Object> object, Class<T> className)
            throws JsonProcessingException {
        return objectMapper.convertValue(object, className);
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> Map<String, Object> convertObjectToMap(T object) {
        return objectMapper.convertValue(object, new TypeReference<>() {});
    }

    public static <T> T convertJsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
