package com.project.oriobook.modules.product.responses;

import com.project.oriobook.core.response.base.BaseResponse;

public interface GetProductResponse extends BaseResponse {
    String getName();

    String getImage();

    Double getPrice();

    String getDescription();

    int getStock();

    CategoryNode getCategoryNode();

    interface CategoryNode extends BaseResponse{
        String getName();
    }
}
