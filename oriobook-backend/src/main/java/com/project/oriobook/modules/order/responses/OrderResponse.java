package com.project.oriobook.modules.order.responses;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponse extends BaseEntity {
    private CommonEnum.OrderStatusEnum status;

    private String note;

    private User userNode;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class User extends BaseEntity {
        String firstName;

        String lastName;

        String email;

        String phone;
    }

    private List<OrderDetails> detailsList = new ArrayList<>();

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OrderDetails extends BaseEntity {
        private Product productNode;

        private int quantity;

        private Double itemTotalPrice;

        private boolean isReview;

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Product extends BaseEntity {
            private String name;

            private String image;

            private Double price;

            private double rating;

            private Category categoryNode;

            @EqualsAndHashCode(callSuper = true)
            @Data
            public static class Category extends BaseEntity {
                private String name;
            }
        }
    }
}
