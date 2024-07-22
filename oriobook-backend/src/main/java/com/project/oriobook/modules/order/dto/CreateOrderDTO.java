package com.project.oriobook.modules.order.dto;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class CreateOrderDTO {
    @Max(value = 255, message = "Note must be <= 255 characters")
    String note;
}
