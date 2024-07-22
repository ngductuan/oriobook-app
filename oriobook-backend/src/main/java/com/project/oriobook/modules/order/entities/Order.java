package com.project.oriobook.modules.order.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
