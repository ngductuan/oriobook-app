package com.project.oriobook.modules.order_details.services;

import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.order.services.OrderService;
import com.project.oriobook.modules.order_details.entities.OrderDetails;
import com.project.oriobook.modules.order_details.repository.OrderDetailsRepository;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailsService implements IOrderDetailsService{
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsService.class);

    @Override
    @Transactional
    public List<OrderDetails> createOrderDetails(Order order, List<CartRedisItem> cartItems)
            throws Exception{
        if(ValidationUtil.isNullOrEmptyList(cartItems)){
            return null;
        }

        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (CartRedisItem item : cartItems){
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderNode(order);

            Product product = productService.getProductById(item.getProductId());
            orderDetails.setProductNode(product);

            orderDetails.setQuantity(item.getQuantity());

            orderDetails.setItemTotalPrice(product.getPrice() * item.getQuantity());

            orderDetailsList.add(orderDetailsRepository.save(orderDetails));
        }

        return orderDetailsList;
    }
}
