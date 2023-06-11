package com.ruoyi.common.exception;

import lombok.Getter;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/10
 */
@Getter
public class ResultException extends RuntimeException {

    private final int code;

    public ResultException(final String message) {
        super(message);
        this.code = 500;
    }

    public ResultException(final String message, final Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public ResultException(int code, final String message) {
        super(message);
        this.code = code;
    }

    public ResultException(int code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
