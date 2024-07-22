package com.project.oriobook.modules.cart.entities;

import com.project.oriobook.modules.product.responses.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private List<CartItem> data;
    private Double totalPrice;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CartItem extends ProductResponse {
        private int quantity;
        private Double itemTotalPrice;
    }
}
