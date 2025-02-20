package com.project.oriobook.modules.cart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("cart")
public class CartRedisItem {
    @Id
    private String id;

    private String productId;

    private int quantity;
}
