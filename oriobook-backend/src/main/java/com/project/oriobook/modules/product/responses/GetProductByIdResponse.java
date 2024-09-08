package com.project.oriobook.modules.product.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.category.entities.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetProductByIdResponse extends GetAllProductsResponse{
    private Category categoryNode;
    private Author authorNode;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Category extends GetAllProductsResponse.Category {
        private String name;
        private Boolean isMain = false;
        private int numProducts = 0;
        List<Category> children = new ArrayList<>();
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Author extends GetAllProductsResponse.Author {
        private String name;
        private String image;
        private String description;
        private String style;
        private String address;
        private String dateOfBirth;
        private CommonEnum.GenderEnum gender;
        private int publishedBook;
    }
}
