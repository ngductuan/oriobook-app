package com.project.oriobook.core.pagination.base;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

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
}
