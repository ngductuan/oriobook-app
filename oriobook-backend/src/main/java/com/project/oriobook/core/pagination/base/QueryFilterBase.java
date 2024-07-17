package com.project.oriobook.core.pagination.base;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.enums.CommonEnum;
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
    @Pattern(regexp = CommonConst.DATE_BOND_REGEX, message = CommonConst.DATE_BOND_MSG)
    protected String startDate;

    @Schema(example = "01/01/2021", format = "date")
    @Pattern(regexp = CommonConst.DATE_BOND_REGEX, message = CommonConst.DATE_BOND_MSG)
    protected String endDate;

    protected CommonEnum.SortEnum sortByDate;
}
