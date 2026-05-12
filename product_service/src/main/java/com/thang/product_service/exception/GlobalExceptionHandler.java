package com.thang.product_service.exception;

import com.thang.product_service.constant.ErrorCode;
import com.thang.product_service.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── Business / Application exceptions ────────────────────────────────────

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResult<?>> handleApplicationException(ApplicationException ex) {
        log.warn("Business exception [{}]: {}", ex.getErrorCode().getCode(), ex.getMessage());
        return ResponseEntity
                .status(ex.getErrorCode().getHttpStatus())
                .body(ApiResult.error(ex.getErrorCode()));
    }

    // ── Bad-request exceptions ────────────────────────────────────────────────

    /** @Valid / @Validated failures on request bodies. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<?>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(ApiResult.errorWithDetails(ErrorCode.VALIDATION_FAILED, fieldErrors));
    }

    /** Malformed JSON body or unreadable content type. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResult<?>> handleNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResult.error(ErrorCode.INVALID_REQUEST, "Malformed or unreadable JSON request"));
    }

    /** Wrong type for a path variable or request param (e.g. non-UUID string). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResult<?>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());
        return ResponseEntity.badRequest()
                .body(ApiResult.error(ErrorCode.INVALID_REQUEST, message));
    }

    // ── Not-found exceptions ──────────────────────────────────────────────────

    /** Spring 6 – unmatched routes. */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResult<?>> handleNoResource(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResult.error(ErrorCode.ROUTE_NOT_FOUND));
    }

    // ── System / catch-all exceptions ─────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<?>> handleException(Exception ex) {
        log.error("Unexpected system error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
