package com.project.oriobook.modules.cart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRedisItem {
    private String productId;

    private int quantity;
}
