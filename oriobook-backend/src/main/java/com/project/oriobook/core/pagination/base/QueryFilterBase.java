package com.project.oriobook.core.pagination.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.converters.DateDTODeserializer;
import com.project.oriobook.common.converters.DateTimeDTODeserializer;
import com.project.oriobook.common.enums.CommonEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QueryFilterBase {
    @Schema(example = "0")
    protected int page = 0;

    @Schema(example = "10")
    protected int limit = 10;

    // @Schema(example = "01/01/2021", format = "date")
    // @Pattern(regexp = CommonConst.DATE_BOND_REGEX, message = CommonConst.DATE_BOND_MSG)
    // @JsonDeserialize(using = DateTimeDTODeserializer.class)
    // protected LocalDateTime startDate;
    // @JsonDeserialize(using = DateDTODeserializer.class)
    // protected LocalDate startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected LocalDateTime startDate;

    @Schema(example = "01/01/2024", format = "date")
    @Pattern(regexp = CommonConst.DATE_BOND_REGEX, message = CommonConst.DATE_BOND_MSG)
    // @JsonDeserialize(using = DateDTODeserializer.class)
    // protected LocalDate endDate;
    protected String endDate;

    protected CommonEnum.SortEnum sortByDate;
}
