package com.project.oriobook.modules.order.services;

import com.project.oriobook.modules.order.dto.CreateOrderDTO;
import com.project.oriobook.modules.order.dto.FindAllOrderQueryDTO;
import com.project.oriobook.modules.order.dto.UpdateOrderDTO;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.user.entities.User;
import org.springframework.data.domain.Page;

public interface IOrderService {
    // For admin
    Page<Order> getAllOrders(FindAllOrderQueryDTO query);
    Order updateOrderStatus(String orderId, UpdateOrderDTO dto) throws Exception;

    // For user
    Page<Order> getAllMyOrders(String userId, FindAllOrderQueryDTO query);
    Order createOrder(User userDetails, CreateOrderDTO order) throws Exception;
    Order getOrderById(String orderId, String userId) throws Exception;
}
