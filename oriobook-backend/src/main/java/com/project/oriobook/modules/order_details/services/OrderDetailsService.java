package com.project.oriobook.modules.order_details.services;

import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.order_details.entities.OrderDetails;

import java.util.List;

public class OrderDetailsService implements IOrderDetailsService{

    @Override
    public List<OrderDetails> createOrderDetails(String userId, List<CartRedisItem> orderDetails) {
        return List.of();
    }
}
