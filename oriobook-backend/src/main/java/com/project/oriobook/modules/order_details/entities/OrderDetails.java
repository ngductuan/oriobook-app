package com.project.oriobook.modules.order_details.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.product.entities.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "order_details")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order orderNode;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productNode;

    private int quantity;

    @JoinColumn(name = "item_total_price")
    private Double itemTotalPrice;

    @JoinColumn(name = "is_review")
    private boolean isReview = false;
}
