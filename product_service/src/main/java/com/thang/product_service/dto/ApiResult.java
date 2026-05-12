package com.thang.product_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thang.product_service.exception.errorcode.BaseErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private T data;
    private Meta meta;
    private ErrorDetail error;

    // ── Inner: Meta ──────────────────────────────────────────────────────────

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        @Builder.Default
        private Long serverTime = System.currentTimeMillis();

        @Builder.Default
        private String apiVersion = "1.0.0";

        @Builder.Default
        private String traceId = UUID.randomUUID().toString();

        private String message;

        // Offset pagination
        private Integer page;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;

        // Sort / filter hints (optional, set by callers)
        private String sort;
        private Map<String, Object> filter;
    }

    // ── Inner: ErrorDetail ───────────────────────────────────────────────────

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetail {
        private String code;
        private String message;
        private String traceId;
        private Object details; // field validation map or any extra context
    }

    // ── Factory: success ─────────────────────────────────────────────────────

    /** Single item, no message. */
    public static <T> ApiResult<T> success(T data) {
        return ApiResult.<T>builder()
                .data(data)
                .meta(Meta.builder().build())
                .build();
    }

    /** Single item with UI message. */
    public static <T> ApiResult<T> success(T data, String message) {
        return ApiResult.<T>builder()
                .data(data)
                .meta(Meta.builder().message(message).build())
                .build();
    }

    /** Single item with fully-custom meta (sort, filter, etc.). */
    public static <T> ApiResult<T> success(T data, Meta meta) {
        return ApiResult.<T>builder()
                .data(data)
                .meta(meta)
                .build();
    }

    /** No data (delete / cancel operations). */
    public static <T> ApiResult<T> success(String message) {
        return ApiResult.<T>builder()
                .meta(Meta.builder().message(message).build())
                .build();
    }

    /** Empty success (no data, no message). */
    public static <T> ApiResult<T> success() {
        return ApiResult.<T>builder()
                .meta(Meta.builder().build())
                .build();
    }

    /** Paginated list from Spring Data {@link Page}. */
    public static <T> ApiResult<List<T>> successPage(Page<T> page) {
        return ApiResult.<List<T>>builder()
                .data(page.getContent())
                .meta(Meta.builder()
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .build())
                .build();
    }

    /** Paginated list with UI message. */
    public static <T> ApiResult<List<T>> successPage(Page<T> page, String message) {
        return ApiResult.<List<T>>builder()
                .data(page.getContent())
                .meta(Meta.builder()
                        .message(message)
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .build())
                .build();
    }

    /** Paginated list where content was already mapped to a different type. */
    public static <R, T> ApiResult<List<R>> successPage(List<R> content, Page<T> pageInfo) {
        return ApiResult.<List<R>>builder()
                .data(content)
                .meta(Meta.builder()
                        .page(pageInfo.getNumber())
                        .size(pageInfo.getSize())
                        .totalElements(pageInfo.getTotalElements())
                        .totalPages(pageInfo.getTotalPages())
                        .build())
                .build();
    }

    // ── Factory: error ───────────────────────────────────────────────────────

    /**
     * Error from a typed error code; optional varargs are forwarded to
     * {@link BaseErrorCode#format} so you can do e.g.
     * {@code ApiResult.error(ErrorCode.PRODUCT_NOT_FOUND, productId)}.
     */
    public static ApiResult<?> error(BaseErrorCode errorCode, Object... args) {
        return ApiResult.builder()
                .error(ErrorDetail.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.format(args))
                        .traceId(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    /**
     * Error with a custom message that overrides the ErrorCode default
     * (e.g. wrapping third-party error text).
     */
    public static ApiResult<?> error(BaseErrorCode errorCode, String customMessage) {
        return ApiResult.builder()
                .error(ErrorDetail.builder()
                        .code(errorCode.getCode())
                        .message(customMessage)
                        .traceId(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    /**
     * Error with structured details — intended for validation failures where
     * {@code details} is a {@code Map<field, message>}.
     */
    public static ApiResult<?> errorWithDetails(BaseErrorCode errorCode, Object details) {
        return ApiResult.builder()
                .error(ErrorDetail.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .traceId(UUID.randomUUID().toString())
                        .details(details)
                        .build())
                .build();
    }
}
