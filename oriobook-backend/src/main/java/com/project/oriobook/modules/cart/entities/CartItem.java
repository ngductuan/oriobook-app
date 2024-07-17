package com.project.oriobook.modules.cart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private String productId;
    private int count;

    public void addItem() {
        this.count += 1;
    }

    public void subtractItem() {
        this.count -= 1;
    }
}
