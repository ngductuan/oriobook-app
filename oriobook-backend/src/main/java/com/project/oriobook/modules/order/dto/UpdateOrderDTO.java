package com.project.oriobook.modules.order.dto;

import com.project.oriobook.common.enums.CommonEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderDTO {
    @NotNull
    @Enumerated(EnumType.STRING)
    private CommonEnum.OrderStatusEnum status;
}
