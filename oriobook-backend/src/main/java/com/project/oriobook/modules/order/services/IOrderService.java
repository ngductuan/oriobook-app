package com.project.oriobook.modules.order.services;

import com.project.oriobook.modules.order.dto.CreateOrderDTO;
import com.project.oriobook.modules.order.dto.FindAllOrderQueryDTO;
import com.project.oriobook.modules.order.entities.Order;
import org.springframework.data.domain.Page;

public interface IOrderService {
    // For admin
    Page<Order> getAllOrders(FindAllOrderQueryDTO query);

    // For user
    Page<Order> getMyOrders(String userId, FindAllOrderQueryDTO query);
    Order createOrder(String userId, CreateOrderDTO order) throws Exception;
}
