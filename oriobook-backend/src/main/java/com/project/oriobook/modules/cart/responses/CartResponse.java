package com.project.oriobook.modules.cart.responses;

import com.project.oriobook.modules.product.responses.GetAllProductsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private List<CartItem> data = new ArrayList<>();
    private double totalPrice;
    private int totalQuantity;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CartItem extends GetAllProductsResponse {
        private int quantity;
        private double itemTotalPrice;
    }
}
