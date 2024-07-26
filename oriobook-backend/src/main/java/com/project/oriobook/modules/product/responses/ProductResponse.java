package com.project.oriobook.modules.product.responses;

import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductResponse extends BaseEntity {
    private String id;

    private String name;

    private String image;

    private Double price;

    private int stock;

    private Category categoryNode;

    private Author authorNode;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Category extends BaseEntity {
        private String name;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Author extends BaseEntity {
        private String name;
    }
}