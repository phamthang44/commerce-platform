package com.thang.product_service.constant;

import com.thang.product_service.exception.errorcode.BaseErrorCode;
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

    // ── Product business ─────────────────────────────────────────────────────
    PRODUCT_NOT_FOUND(404, "PRODUCT_NOT_FOUND", "Product not found"),

    // ── Category business ────────────────────────────────────────────────────
    CATEGORY_NOT_FOUND(404, "CATEGORY_NOT_FOUND", "Category not found"),
    CATEGORY_INACTIVE(400, "CATEGORY_INACTIVE", "Category is not active"),
    CATEGORY_HAS_PRODUCTS(400, "CATEGORY_HAS_PRODUCTS", "Category has associated products and cannot be deleted");

    private final int httpStatus;
    private final String code;
    private final String message;
}
