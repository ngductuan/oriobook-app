package com.project.oriobook.modules.category.dto;

import com.project.oriobook.core.pagination.base.QueryFilterBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindAllCategoryQueryDTO extends QueryFilterBase {
    private String categoryName;
}
