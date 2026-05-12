package com.thang.order_service.constant;

import com.thang.order_service.exception.errorcode.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {

    // ── System ───────────────────────────────────────────────────────────────
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "An unexpected error occurred"),

    // ── Request / Validation ─────────────────────────────────────────────────
    VALIDATION_FAILED(400, "VALIDATION_FAILED", "Request validation failed"),
    INVALID_REQUEST(400, "INVALID_REQUEST", "Invalid request"),
    ROUTE_NOT_FOUND(404, "ROUTE_NOT_FOUND", "The requested endpoint does not exist"),

    // ── Order business ───────────────────────────────────────────────────────
    ORDER_NOT_FOUND(404, "ORDER_NOT_FOUND", "Order not found"),
    ORDER_CANNOT_BE_CANCELLED(400, "ORDER_CANNOT_BE_CANCELLED", "Order cannot be cancelled in its current status"),
    INVALID_ORDER_STATUS(400, "INVALID_ORDER_STATUS", "Invalid order status");

    private final int httpStatus;
    private final String code;
    private final String message;
}
