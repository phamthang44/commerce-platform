package com.thang.order_service.service.impl;

import com.thang.order_service.clients.ProductClient;
import com.thang.order_service.common.enums.OrderStatus;
import com.thang.order_service.constant.ErrorCode;
import com.thang.order_service.dto.client.request.ProductDeductRequest;
import com.thang.order_service.dto.client.request.ProductFilter;
import com.thang.order_service.dto.client.response.ProductDTO;
import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.dto.request.OrderItemRequest;
import com.thang.order_service.dto.request.UpdateOrderStatusRequest;
import com.thang.order_service.dto.response.OrderResponse;
import com.thang.order_service.entity.Order;
import com.thang.order_service.entity.OrderItem;
import com.thang.order_service.exception.ApplicationException;
import com.thang.order_service.mapper.OrderMapper;
import com.thang.order_service.repository.OrderRepository;
import com.thang.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerId());

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatus.NEW);
        order.setTotalAmount(BigDecimal.ZERO);

        List<UUID> productIds = request.getItems().stream()
                .map(OrderItemRequest::getProductId)
                .toList();

        List<ProductDTO> products = productClient.getProductsByIds(
                ProductFilter.builder().productIds(productIds).build());

        Map<UUID, ProductDTO> productMap = new HashMap<>();
        products.forEach(p -> productMap.put(p.getId(), p));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {

            // look up this product from the service response
            ProductDTO product = productMap.get(itemReq.getProductId());

            // basic validation against product service data
            if (product == null) {
                throw new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND);
            }
            if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApplicationException(ErrorCode.PRODUCT_UNAVAILABLE);
            }
            if (itemReq.getQuantity() > product.getStockQuantity()) {
                throw new ApplicationException(ErrorCode.INSUFFICIENT_STOCK);
            }

            productClient.deductStock(
                    ProductDeductRequest.builder()
                            .productId(product.getId())
                            .quantity(itemReq.getQuantity())
                            .build());

            // set data from product service into OrderItem
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }

        // Step 8: attach computed items and total, then save
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID id) {
        log.info("Fetching order: {}", id);
        return orderMapper.toOrderResponse(
                orderRepository.findById(id)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(int page, int size) {
        log.info("Fetching all orders page={} size={}", page, size);
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(UUID id, UpdateOrderStatusRequest request) {
        log.info("Updating order {} status to {}", id, request.getStatus());
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));

        //TODO: validate có đc chuyển trạng thái ko ....
        order.setStatus(request.getStatus());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void cancelOrder(UUID id) {
        log.info("Cancelling order: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));
        if (!"PENDING".equals(order.getStatus())) {
            throw new ApplicationException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
