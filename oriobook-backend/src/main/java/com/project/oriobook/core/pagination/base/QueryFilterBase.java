package com.project.oriobook.core.pagination.base;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.utils.EnumUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QueryFilterBase {
    @Schema(example = "0")
    protected int page = 0;

    @Schema(example = "10")
    protected int limit = 10;

    @Schema(example = "01/01/2021", format = "date")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Invalid date format, expected dd/MM/yyyy")
    protected String startDate;

    @Schema(example = "01/01/2021", format = "date")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Invalid date format, expected dd/MM/yyyy")
    protected String endDate;

    protected CommonEnum.SortEnum sortByDate;

    public void setSortByDate(String sortByDate) {
        this.sortByDate = EnumUtil.fromValue(CommonEnum.SortEnum.class, sortByDate);
    }
}
