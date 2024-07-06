package com.project.oriobook.core.pagination.base;

import com.project.oriobook.common.enums.CommonEnum;
import lombok.*;

import java.time.LocalDate;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
// @Builder
public class QueryFilterBase {
    protected int page;
    protected int limit;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected CommonEnum.SortEnum sortByDate = CommonEnum.SortEnum.SORT_DESC;
}
