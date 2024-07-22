package com.project.oriobook.modules.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMainCategoryDTO {
    @NotNull
    @NotBlank
    @Schema(example = "Fiction")
    private String name;

    @JsonIgnore
    private boolean isMain = true;
}
