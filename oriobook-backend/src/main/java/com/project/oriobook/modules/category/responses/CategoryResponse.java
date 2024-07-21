package com.project.oriobook.modules.category.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryResponse extends BaseEntity {
    private String id;

    private String name;

    @JsonProperty("isMain")
    private boolean isMain;

    private int numProducts;

    private List<CategoryResponse> children;
}
