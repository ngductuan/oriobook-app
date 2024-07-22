package com.project.oriobook.modules.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.oriobook.common.annotations.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSubCategoryDTO {
    @NotNull
    @NotBlank
    @Schema(example = "Fiction")
    private String name;

    @JsonIgnore
    private boolean isMain = false;

    @NotNull
    @ValidUUID
    @Schema(example = "b427f457-5bc4-487e-b5f0-bc43f1c603d0")
    private String parentId;
}
