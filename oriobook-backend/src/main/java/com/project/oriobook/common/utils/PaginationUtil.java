package com.project.oriobook.common.utils;

import com.project.oriobook.core.pagination.base.QueryFilterBase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {
    public static PageRequest generatePageRequest(QueryFilterBase query, List<Sort.Order> extraOrders){
        List<Sort.Order> baseOrders = new ArrayList<>();
        if(!ValidationUtil.isNullOrBlankStr(query.getSortByDate())){
            baseOrders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByDate().toString()),"createdAt"));
        }

        baseOrders.addAll(extraOrders);

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(baseOrders));
        return pageRequest;
    }
}
