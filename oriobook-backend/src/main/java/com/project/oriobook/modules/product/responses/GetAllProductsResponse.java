package com.project.oriobook.modules.product.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetAllProductsResponse extends BaseEntity {
    protected String name;

    protected String image;

    protected Double price;

    protected String description;

    protected double rating;

    protected int stock;

    protected Category categoryNode;

    protected Author authorNode;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Category extends BaseEntity {
        protected String name;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Author extends BaseEntity {
        protected String name;
    }
}