package com.project.oriobook.modules.product.dto;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.utils.EnumUtil;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindAllProductQueryDTO extends QueryFilterBase {
    private String productName;
    private String authorId;
    private String categoryId;
    private CommonEnum.SortEnum sortByRating;
    private CommonEnum.SortEnum sortByPrice;

    public void setSortByRating(String sortByRating) {
        this.sortByRating = EnumUtil.fromValue(CommonEnum.SortEnum.class, sortByRating);
    }

    public void setSortByPrice(String sortByPrice) {
        this.sortByPrice = EnumUtil.fromValue(CommonEnum.SortEnum.class, sortByPrice);
    }
}
