package com.thang.product_service.exception.errorcode;

public interface BaseErrorCode {
    String getCode();
    int getHttpStatus();
    String getMessage();

    default String format(Object... args) {
        if (args == null || args.length == 0) return getMessage();
        try {
            return String.format(getMessage(), args);
        } catch (Exception ignored) {
            return getMessage();
        }
    }
}
