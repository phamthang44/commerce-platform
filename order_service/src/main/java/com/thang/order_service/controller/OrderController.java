package com.thang.order_service.controller;

import com.thang.order_service.constant.ApiRoutes;
import com.thang.order_service.dto.BaseResponse;
import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.dto.response.OrderResponse;
import com.thang.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.ORDERS)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        log.info("POST {}", ApiRoutes.ORDERS);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<OrderResponse>builder()
                        .data(orderService.createOrder(request))
                        .message("Order Created Success")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderResponse>> getOrder(@PathVariable UUID id) {
        log.info("GET {}", ApiRoutes.ORDER_BY_ID);

        return ResponseEntity.ok(
                BaseResponse.<OrderResponse>builder()
                        .data(orderService.getOrderById(id))
                        .message("Order Fetched Success")
                        .build());
    }
}
