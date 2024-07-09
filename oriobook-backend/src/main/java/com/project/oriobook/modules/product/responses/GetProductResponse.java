package com.project.oriobook.modules.product.responses;

import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetProductResponse extends BaseEntity {
    private String id;

    private String name;

    private String image;

    private Double price;

    private String description;

    private int stock;

    private Category categoryNode;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Category extends BaseEntity {
        private String name;
    }
}