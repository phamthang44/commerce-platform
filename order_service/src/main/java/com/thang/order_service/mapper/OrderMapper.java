package com.thang.order_service.mapper;

import com.thang.order_service.dto.response.OrderItemResponse;
import com.thang.order_service.dto.response.OrderResponse;
import com.thang.order_service.entity.Order;
import com.thang.order_service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "id", source = "id")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
