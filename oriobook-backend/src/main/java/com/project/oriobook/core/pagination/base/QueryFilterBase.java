package com.project.oriobook.core.pagination.base;

import com.project.oriobook.common.enums.CommonEnum;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryFilterBase {
    protected int page = 0;
    protected int limit = 10;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected CommonEnum.SortEnum sortByDate;
}
