package com.thang.order_service.dto.request;

import com.thang.order_service.common.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    @NotBlank
    private OrderStatus status;
}
