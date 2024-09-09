package com.project.oriobook.modules.order.dto;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FindAllOrderQueryDTO extends QueryFilterBase {
    @NonNull
    @NotBlank
    private CommonEnum.OrderStatusEnum status;
}
