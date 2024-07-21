package com.project.oriobook.modules.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.http.MediaType;

@Data
public class CreateMainCategoryDTO {
    @NotNull
    @NotBlank
    @Schema(example = "Fiction")
    private String name;

    @JsonIgnore
    private boolean isMain = true;
}
