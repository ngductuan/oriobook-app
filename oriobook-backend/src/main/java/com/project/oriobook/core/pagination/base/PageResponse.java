package com.project.oriobook.core.pagination.base;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.oriobook.common.utils.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
