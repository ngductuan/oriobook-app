package com.project.oriobook.modules.order.services;

import com.project.oriobook.common.utils.QueryUtil;
import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.cart.services.CartRedisService;
import com.project.oriobook.modules.order.dto.CreateOrderDTO;
import com.project.oriobook.modules.order.dto.FindAllOrderQueryDTO;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.order.repository.OrderRepository;
import com.project.oriobook.modules.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartRedisService cartRedisService;

    private final ModelMapper modelMapper;

    @Override
    public Page<Order> getAllOrders(FindAllOrderQueryDTO query) {
        if (query == null) {
            return orderRepository.findAll(new FindAllOrderQueryDTO(), Pageable.unpaged());
        }

        List<Sort.Order> orders = QueryUtil.parseSortBase(query);

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(orders));
        Page<Order> orderPaging = orderRepository.findAll(query, pageRequest);

        return orderPaging;
    }

    @Override
    public Page<Order> getMyOrders(String userId, FindAllOrderQueryDTO query) {
        if (query == null) {
            return orderRepository.findMyOrder(userId, new FindAllOrderQueryDTO(), Pageable.unpaged());
        }

        List<Sort.Order> orders = QueryUtil.parseSortBase(query);

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(orders));
        Page<Order> orderPaging = orderRepository.findMyOrder(userId, query, pageRequest);

        return orderPaging;
    }

    @Override
    @Transactional
    public Order createOrder(String userId, CreateOrderDTO order) throws Exception {
        // Order newOrder =
        List<CartRedisItem> cartItems = cartRedisService.getCart(userId);

        return null;
    }
}
