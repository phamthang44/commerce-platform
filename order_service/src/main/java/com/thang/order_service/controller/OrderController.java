package com.thang.order_service.controller;

import com.thang.order_service.constant.ApiRoutes;
import com.thang.order_service.dto.ApiResult;
import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.dto.request.UpdateOrderStatusRequest;
import com.thang.order_service.dto.response.OrderResponse;
import com.thang.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.ORDERS)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResult<OrderResponse>> createOrder(
            @RequestBody @Valid CreateOrderRequest request) {
        log.info("POST {}", ApiRoutes.ORDERS);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(orderService.createOrder(request), "Order created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<OrderResponse>> getOrder(@PathVariable UUID id) {
        log.info("GET {}/{}", ApiRoutes.ORDERS, id);
        return ResponseEntity.ok(
                ApiResult.success(orderService.getOrderById(id), "Order fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<OrderResponse>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET {} page={} size={}", ApiRoutes.ORDERS, page, size);
        return ResponseEntity.ok(
                ApiResult.successPage(orderService.getAllOrders(page, size), "Orders fetched successfully"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResult<OrderResponse>> updateOrderStatus(
            @PathVariable UUID id, @RequestBody @Valid UpdateOrderStatusRequest request) {
        log.info("PATCH {}/{}/status", ApiRoutes.ORDERS, id);
        return ResponseEntity.ok(
                ApiResult.success(orderService.updateOrderStatus(id, request), "Order status updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<?>> cancelOrder(@PathVariable UUID id) {
        log.info("DELETE {}/{}", ApiRoutes.ORDERS, id);
        orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResult.success("Order cancelled successfully"));
    }
}
