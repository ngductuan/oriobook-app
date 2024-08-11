package com.project.oriobook.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.converters.LocalDateTimeConverter;
import com.project.oriobook.core.entity.base.BaseEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
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

    public static ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Register the custom converter
        Converter<String, LocalDateTime> toLocalDateTime = new LocalDateTimeConverter();

        // Apply to BaseEntity and all its subclasses
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.addConverter(toLocalDateTime);

        // Create TypeMap for Map<String, Object> to BaseEntity
        // TypeMap<Map<String, Object>, BaseEntity> typeMap = modelMapper.createTypeMap(Map.class, BaseEntity.class);
        //
        // typeMap.addMappings(mapper -> {
        //     mapper.using(toLocalDateTime).map(src -> src.get("createdAt"), BaseEntity::setCreatedAt);
        //     mapper.using(toLocalDateTime).map(src -> src.get("updatedAt"), BaseEntity::setUpdatedAt);
        // });

        return modelMapper;
    }
}
