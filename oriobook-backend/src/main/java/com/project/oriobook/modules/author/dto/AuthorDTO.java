package com.project.oriobook.modules.author.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.oriobook.common.annotations.ValidDateFormat;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.converters.DateDTODeserializer;
import com.project.oriobook.common.enums.CommonEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AuthorDTO {
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

    // @Schema(example = "01/01/2024", format = "date")
    // @Pattern(regexp = CommonConst.DATE_BOND_REGEX, message = CommonConst.DATE_BOND_MSG)
    // @ValidDateFormat
    @JsonDeserialize(using = DateDTODeserializer.class)
    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @NotNull
    private CommonEnum.GenderEnum gender;
}
