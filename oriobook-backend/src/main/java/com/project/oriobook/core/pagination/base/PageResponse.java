package com.project.oriobook.core.pagination.base;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.oriobook.common.utils.MapperUtil;
import com.project.oriobook.modules.product.responses.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T>{
    private int currentPage;
    private int pageSize;
    private List<T> data;
    private int totalPages;
    private int totalRecords;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

    public PageResponse(Page<T> data){
        this.currentPage = data.getNumber();
        this.pageSize = data.getSize();
        this.data = data.getContent();
        this.totalPages = data.getTotalPages();
        this.totalRecords = (int) data.getTotalElements();
        this.hasPreviousPage = data.hasPrevious();
        this.hasNextPage = data.hasNext();
    }

    public void setResponseForElastic(SearchResponse<ObjectNode> response, int currentPage,
                                      int pageSize, Class<T> targetClass) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        if(response.hits() == null) return;

        // Set data from the hits in the response
        List<ObjectNode> objectNodes = response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        // Convert to map
        List<Map<String, Object>> maps = objectNodes.stream()
                .map(MapperUtil::convertObjectToMap)
                .toList();

        // Start mapping
        ModelMapper modelMapper = new ModelMapper();

        // modelMapper.typeMap(Map.class, targetClass).addMappings(mapper -> {
        //     mapper.map(src -> LocalDateTime.parse((String) src.get("createdAt"), DateTimeFormatter.ISO_DATE_TIME),
        //             (dest, value) -> {
        //                 try {
        //                     Method setter = targetClass.getMethod("setCreatedAt", LocalDateTime.class);
        //                     setter.invoke(dest, LocalDateTime.parse(value.toString()));
        //                 } catch (Exception e) {
        //                     throw new RuntimeException("Error setting createdAt", e);
        //                 }
        //             });
        //     mapper.map(src -> LocalDateTime.parse((String) src.get("updatedAt"), DateTimeFormatter.ISO_DATE_TIME),
        //             (dest, value) -> {
        //                 try {
        //                     Method setter = targetClass.getMethod("setUpdatedAt", LocalDateTime.class);
        //                     setter.invoke(dest, LocalDateTime.parse(value.toString()));
        //                 } catch (Exception e) {
        //                     throw new RuntimeException("Error setting updatedAt", e);
        //                 }
        //             });
        // });
        //
        // List<T> mappedLists = maps.stream()
        //         .map(map -> modelMapper.map(map, targetClass))
        //         .collect(Collectors.toList());

        // List<T> mappedLists = maps.stream().map(map -> {
        //     // Create an instance of the target class
        //     T instance;
        //     try {
        //         instance = targetClass.getDeclaredConstructor().newInstance();
        //     } catch (Exception e) {
        //         throw new RuntimeException("Error creating instance of target class", e);
        //     }
        //
        //     // Map individual fields
        //     if (map.containsKey("createdAt")) {
        //         try {
        //             Method setter = targetClass.getMethod("setCreatedAt", LocalDateTime.class);
        //             LocalDateTime createdAt = LocalDateTime.parse((String) map.get("createdAt"), DateTimeFormatter.ISO_DATE_TIME);
        //             setter.invoke(instance, createdAt);
        //         } catch (Exception e) {
        //             throw new RuntimeException("Error setting createdAt", e);
        //         }
        //     }
        //
        //     if (map.containsKey("updatedAt")) {
        //         try {
        //             Method setter = targetClass.getMethod("setUpdatedAt", LocalDateTime.class);
        //             LocalDateTime updatedAt = LocalDateTime.parse((String) map.get("updatedAt"), DateTimeFormatter.ISO_DATE_TIME);
        //             setter.invoke(instance, updatedAt);
        //         } catch (Exception e) {
        //             throw new RuntimeException("Error setting updatedAt", e);
        //         }
        //     }
        //
        //     // Use ModelMapper for other fields
        //     modelMapper.map(map, instance);
        //
        //     return instance;
        // }).collect(Collectors.toList());

        modelMapper.typeMap(ObjectNode.class, targetClass);
        ObjectMapper objectMapper = new ObjectMapper();

        List<T> mappedLists = objectNodes.stream()
                .map(map -> {
                    return objectMapper.convertValue(map, targetClass);
                }).toList();

        this.data = mappedLists;

        this.totalRecords = (int) response.hits().total().value();
        this.totalPages = (int) Math.ceil((double) this.totalRecords / pageSize);

        this.hasPreviousPage = currentPage > 0;
        this.hasNextPage = currentPage < this.totalPages - 1;
    }
}
