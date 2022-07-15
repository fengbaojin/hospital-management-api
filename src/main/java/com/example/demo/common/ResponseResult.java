package com.example.demo.common;


import java.security.InvalidParameterException;

/**
 * @author fengbaojin
 * 2021/12/21.
 */
public class ResponseResult<T> {
    private int code;
    private String error;
    private String error_description;
    private T data;

    public static ResponseResult error(int code, String error, String error_description) {
        if (code == 0) {
            throw new InvalidParameterException("失败的错误码不能等于0");
        } else {
            return new ResponseResult(code, error, error_description);
        }
    }

    public static ResponseResult error(int code, String error_description) {
        return error(code, error_description, error_description);
    }

    public static ResponseResult error(String error, String error_description) {
        return error(-1, error, error_description);
    }

    public static ResponseResult error(String message) {
        return error(message, message);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult(data);
    }

    public static <T> ResponseResult<T> error(T data) {
        return new ResponseResult(data);
    }

    public static ResponseResult success() {
        return success("");
    }

    public ResponseResult(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public ResponseResult(int code, String error, String error_description) {
        this(code, error);
        this.error_description = error_description;
    }

    public ResponseResult(int code, String message, String debugMessge, T data) {
        this(code, message, debugMessge);
        this.data = data;
    }

    public ResponseResult(T data) {
        this.code = 0;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", error_description='" + error_description + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return this.error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
