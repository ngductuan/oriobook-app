package com.project.oriobook.modules.order.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.order_details.entities.OrderDetails;
import com.project.oriobook.modules.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private CommonEnum.OrderStatusEnum status = CommonEnum.OrderStatusEnum.PENDING;

    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userNode;

    @JoinColumn(name = "total_price")
    private Double totalPrice;

    @OneToMany(mappedBy = "orderNode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetails> detailsList = new ArrayList<>();
}
