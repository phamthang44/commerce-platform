package com.thang.order_service.controller;

import com.thang.order_service.dto.BaseResponse;
import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.entity.Order;
import com.thang.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<Order>> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        log.info("POST /v1/orders called");

        var response = orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<Order>builder()
                        .data(response)
                        .message("Order Created Success")
                        .build());
    }
}
