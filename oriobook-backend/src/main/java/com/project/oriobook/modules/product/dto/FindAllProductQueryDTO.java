package com.project.oriobook.modules.product.dto;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindAllProductQueryDTO extends QueryFilterBase {
    private String productName;

    private String categoryId;

    private String authorId;

    // chưa làm
    private CommonEnum.SortEnum sortByRating;

    private CommonEnum.SortEnum sortByPrice;
}
