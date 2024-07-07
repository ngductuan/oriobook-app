package com.project.oriobook.common.helpers;

import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
    public static List<Sort.Order> parseSortBase(FindAllProductQueryDTO query){
        List<Sort.Order> orders = new ArrayList<>();
        if(ValidationUtil.isNullOrBlank(query.getSortByDate())){
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByDate().toString()),"createdAt"));
        }
        return orders;
    }
}
