package com.project.oriobook.modules.product.dto;

import com.project.oriobook.common.annotations.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDTO {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String image;

    @NotNull
    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    private double price;

    @NotNull
    @PositiveOrZero(message = "Stock must be greater than or equal to 0")
    private int stock;

    @NotNull
    @ValidUUID
    private String categoryId;

    @NotNull
    @ValidUUID
    private String authorId;
}
