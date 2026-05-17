package com.thang.order_service.constant;

import com.thang.order_service.exception.errorcode.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {

    // ── System (001-009) ─────────────────────────────────────────────────────
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR_001", "An unexpected error occurred"),

    // ── Request / Validation (010-019) ───────────────────────────────────────
    VALIDATION_FAILED(400,  "VALIDATION_FAILED_010",  "Request validation failed"),
    INVALID_REQUEST(400,    "INVALID_REQUEST_011",     "Invalid request"),
    ROUTE_NOT_FOUND(404,    "ROUTE_NOT_FOUND_012",     "The requested endpoint does not exist"),

    // ── Order business (100-199) ─────────────────────────────────────────────
    ORDER_NOT_FOUND(404,            "ORDER_NOT_FOUND_100",            "Order not found"),
    ORDER_CANNOT_BE_CANCELLED(400,  "ORDER_CANNOT_BE_CANCELLED_101",  "Order cannot be cancelled in its current status"),
    INVALID_ORDER_STATUS(400,       "INVALID_ORDER_STATUS_102",       "Invalid order status"),
    

    // ── Product business (200-299) ───────────────────────────────────────────
    PRODUCT_NOT_FOUND(404,     "PRODUCT_NOT_FOUND_200",     "Product not found"),
    PRODUCT_UNAVAILABLE(400,   "PRODUCT_UNAVAILABLE_201",   "Product is not available for purchase"),
    INSUFFICIENT_STOCK(400,    "INSUFFICIENT_STOCK_202",    "Insufficient stock for product");

    private final int httpStatus;
    private final String code;
    private final String message;
}
