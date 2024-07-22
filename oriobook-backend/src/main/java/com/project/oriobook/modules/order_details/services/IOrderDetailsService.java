package com.project.oriobook.modules.order_details.services;

import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.order_details.entities.OrderDetails;

import java.util.List;

public interface IOrderDetailsService {
    List<OrderDetails> createOrderDetails(Order order, List<CartRedisItem> cartItems) throws Exception;
}
