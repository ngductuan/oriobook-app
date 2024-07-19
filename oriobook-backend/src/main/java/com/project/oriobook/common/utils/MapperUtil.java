package com.project.oriobook.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.modules.cart.entities.CartItem;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertMapToJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> T convertMapToObject(Map<String, Object> object, Class<T> className) throws JsonProcessingException {
        return (T) objectMapper.convertValue(object, className);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    // public Map convertObjectToMap(Object object) {
    //     // Use Jackson ObjectMapper to convert object to Map
    //     // return objectMapper.convertValue(object, new TypeReference<>() {});
    //     return objectMapper.convertValue(object, Map.class);
    // }

    public <T> Map<String, Object> convertObjectToMap(T object) {
        return objectMapper.convertValue(object, new TypeReference<>() {});
    }

    public <T> T convertJsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
