package com.thang.product_service.constant;

import com.thang.product_service.exception.errorcode.BaseErrorCode;
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

    // ── Product business (200-299) ───────────────────────────────────────────
    PRODUCT_NOT_FOUND(404,    "PRODUCT_NOT_FOUND_200",    "Product not found"),
    INSUFFICIENT_STOCK(400,   "INSUFFICIENT_STOCK_201",   "Insufficient stock for the requested quantity"),

    // ── Category business (300-399) ──────────────────────────────────────────
    CATEGORY_NOT_FOUND(404,      "CATEGORY_NOT_FOUND_300",      "Category not found"),
    CATEGORY_INACTIVE(400,       "CATEGORY_INACTIVE_301",       "Category is not active"),
    CATEGORY_HAS_PRODUCTS(400,   "CATEGORY_HAS_PRODUCTS_302",   "Category has associated products and cannot be deleted");

    private final int httpStatus;
    private final String code;
    private final String message;
}
