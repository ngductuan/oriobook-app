package com.project.oriobook.modules.order.responses;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
