package com.alan.entity;

import lombok.Data;
import lombok.ToString;

import javax.servlet.ServletException;

@Data
@ToString
public class BizException extends ServletException {

    private int code;

    private String message;

    public BizException() {
    }

    public BizException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(int code, Throwable rootCause) {
        super(rootCause);
        this.code = code;
        this.message = rootCause.getMessage();
    }

    public BizException(int code, String message, Throwable rootCause) {
        super(rootCause);
        this.code = code;
        this.message = message;
    }
}
