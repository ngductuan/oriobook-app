package com.project.oriobook.modules.author.dto;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.utils.CommonUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAuthorDTO {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String image;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String style;

    @NotNull
    @NotBlank
    private String address;

    @Schema(example = CommonConst.DATE_EXAMPLE, format = "date")
    @Pattern(regexp = CommonConst.DATE_BOND_REGEX, message = CommonConst.DATE_BOND_MSG)
    private String dateOfBirth;

    @NotNull
    private CommonEnum.GenderEnum gender;

    public LocalDate getDateOfBirth() {
        return CommonUtil.convertStringToDate(dateOfBirth);
    }
}
