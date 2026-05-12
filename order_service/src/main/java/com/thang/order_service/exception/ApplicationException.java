package com.thang.order_service.exception;

import com.thang.order_service.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final BaseErrorCode errorCode;
    private final Object[] args;

    /** Throw with default error-code message (most common). */
    public ApplicationException(BaseErrorCode errorCode, Object... args) {
        super(errorCode.format(args));
        this.errorCode = errorCode;
        this.args = args;
    }

    /** Throw while preserving the original cause for logging. */
    public ApplicationException(BaseErrorCode errorCode, Throwable cause, Object... args) {
        super(errorCode.format(args), cause);
        this.errorCode = errorCode;
        this.args = args;
    }
}
