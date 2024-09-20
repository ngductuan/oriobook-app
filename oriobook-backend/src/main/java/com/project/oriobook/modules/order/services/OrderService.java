package com.project.oriobook.modules.order.services;

import com.project.oriobook.common.exceptions.OrderException;
import com.project.oriobook.common.utils.CommonUtil;
import com.project.oriobook.common.utils.PaginationUtil;
import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.cart.services.CartRedisService;
import com.project.oriobook.modules.order.dto.CreateOrderDTO;
import com.project.oriobook.modules.order.dto.FindAllOrderQueryDTO;
import com.project.oriobook.modules.order.dto.UpdateOrderDTO;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.order.repository.OrderRepository;
import com.project.oriobook.modules.order_details.entities.OrderDetails;
import com.project.oriobook.modules.order_details.services.OrderDetailsService;
import com.project.oriobook.modules.product.services.ProductService;
import com.project.oriobook.modules.user.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartRedisService cartRedisService;
    private final OrderDetailsService orderDetailsService;

    @Override
    public Page<Order> getAllOrders(FindAllOrderQueryDTO query) {
        if (query == null) {
            return orderRepository.findAll(new FindAllOrderQueryDTO(), Pageable.unpaged());
        }

        PageRequest pageRequest = PaginationUtil.generatePageRequest(query, new ArrayList<>());
        Page<Order> orderPaging = orderRepository.findAll(query, pageRequest);

        return orderPaging;
    }

    @Override
    @Transactional
    public Order updateOrderStatus(String orderId, UpdateOrderDTO dto) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException.OrderNotFound(orderId));

        order.setStatus(dto.getStatus());

        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllMyOrders(String userId, FindAllOrderQueryDTO query) {
        if (query == null) {
            return orderRepository.findAllMyOrders(userId, new FindAllOrderQueryDTO(), Pageable.unpaged());
        }

        PageRequest pageRequest = PaginationUtil.generatePageRequest(query, new ArrayList<>());
        Page<Order> orderPaging = orderRepository.findAllMyOrders(userId, query, pageRequest);

        return orderPaging;
    }

    @Override
    @Transactional
    public Order createOrder(User userDetails, CreateOrderDTO dto) throws Exception {
        // Get cart items
        List<CartRedisItem> cartItems = cartRedisService.getCart(userDetails.getId());

        if (ValidationUtil.isNullOrEmptyList(cartItems)) {
            throw new OrderException.EmptyCart();
        }

        // Save order
        Order newOrder = new Order();
        newOrder.setUserNode(userDetails);
        newOrder.setNote(dto.getNote());
        orderRepository.save(newOrder);

        // Create order details
        List<OrderDetails> orderDetailsList = orderDetailsService.createOrderDetails(newOrder, cartItems);

        if (ValidationUtil.isNullOrEmptyList(orderDetailsList)) {
            throw new OrderException.CreateOrderDetailsFailed();
        }

        // Update order
        Double totalPrice = 0.0;
        for (OrderDetails orderDetails : orderDetailsList) {
            totalPrice += orderDetails.getItemTotalPrice();
        }

        newOrder.setTotalPrice(CommonUtil.roundPrice(totalPrice));

        // Remove cart
        cartRedisService.deleteCart(userDetails.getId());

        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrderById(String orderId, String userId) throws Exception {
        Order order = orderRepository.findByIdAndUserIdEquals(orderId, userId)
                .orElseThrow(() -> new OrderException.OrderNotFound(orderId));
        // open lazy orderDetails if it set to lazy
        // Hibernate.initialize(order.getDetailsList());
        return order;
    }
}
