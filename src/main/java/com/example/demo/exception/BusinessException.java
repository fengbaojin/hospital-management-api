package com.example.demo.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误编码
     */
    private String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
}
