package com.project.oriobook.modules.order;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.order.dto.CreateOrderDTO;
import com.project.oriobook.modules.order.dto.FindAllOrderQueryDTO;
import com.project.oriobook.modules.order.dto.UpdateOrderDTO;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.order.responses.OrderResponse;
import com.project.oriobook.modules.order.services.IOrderService;
import com.project.oriobook.modules.order.services.OrderService;
import com.project.oriobook.modules.order_details.entities.OrderDetails;
import com.project.oriobook.modules.user.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "orders")
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    private final ModelMapper modelMapper;

    // For admin
    @GetMapping("/manage")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<OrderResponse> getAllOrders(@ParameterObject @ModelAttribute FindAllOrderQueryDTO query) {
        Page<Order> ordersList = orderService.getAllOrders(query);

        modelMapper.typeMap(Order.class, OrderResponse.class);
        Page<OrderResponse> orderResponse = ordersList.map(order ->
                modelMapper.map(order, OrderResponse.class));

        return new PageResponse<>(orderResponse);
    }

    // For user
    @GetMapping("")
    @Operation(summary = RoleConst.OP_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<OrderResponse> getMyOrders(@ParameterObject @ModelAttribute FindAllOrderQueryDTO query,
                                                   @AuthenticationPrincipal User userDetails) {
        Page<Order> ordersList = orderService.getAllMyOrders(userDetails.getId(), query);

        modelMapper.typeMap(Order.class, OrderResponse.class);
        Page<OrderResponse> orderResponse = ordersList.map(order ->
                modelMapper.map(order, OrderResponse.class));

        return new PageResponse<>(orderResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = RoleConst.OP_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderDetails(@PathVariable String id, @AuthenticationPrincipal User userDetails) throws Exception {
        Order order = orderService.getOrderById(id, userDetails.getId());

        modelMapper.typeMap(Order.class, OrderResponse.class);
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);

        return orderResponse;
    }

    @PostMapping("")
    @Operation(summary = RoleConst.OP_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_USER)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createOrder(@RequestBody CreateOrderDTO dto, @AuthenticationPrincipal User userDetails)
            throws Exception {
        Order order = orderService.createOrder(userDetails, dto);
        return order != null;
    }

    @PutMapping("/{id}/status")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean updateOrderStatus(@PathVariable String id, @RequestBody UpdateOrderDTO dto) throws Exception {
        Order order = orderService.updateOrderStatus(id ,dto);
        return order != null;
    }
}
