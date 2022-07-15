package com.example.demo.exception;

import com.example.demo.common.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseResult handle(BusinessException se){
        return ResponseResult.error(se.getCode(), se.getMessage());
    }

}
