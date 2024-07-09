package com.project.oriobook.modules.product.responses;

import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.core.response.base.BaseResponse;
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

// public interface Category extends BaseResponse {
//     String getName();
// }


// String getName();
//
// String getImage();
//
// Double getPrice();
//
// String getDescription();
//
// int getStock();
//
// CategoryNode getCategoryNode();
//
// void setCategoryNode(Object o);
//
// interface CategoryNode extends BaseResponse {
//     String getName();
// }