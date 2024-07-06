package com.project.oriobook.modules.product.dto;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import lombok.*;

// @Data
// @EqualsAndHashCode(callSuper = true)
// // @AllArgsConstructor
@Getter
@Setter
public class FindAllProductQueryDTO extends QueryFilterBase {
    private String productName;
    private String authorId;
    private String categoryId;
    private CommonEnum.SortEnum sortByRating;
    private CommonEnum.SortEnum sortByPrice;
    private int limit2;
}
