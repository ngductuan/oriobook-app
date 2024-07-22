package com.project.oriobook.common.utils;

import com.project.oriobook.core.pagination.base.QueryFilterBase;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    public static List<Sort.Order> parseSortBase(QueryFilterBase query){
        List<Sort.Order> orders = new ArrayList<>();
        if(!ValidationUtil.isNullOrBlankStr(query.getSortByDate())){
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByDate().toString()),"createdAt"));
        }
        return orders;
    }
}
