package com.example.demo.controller;

import com.example.demo.common.ResponseResult;
import com.example.demo.service.AsyncExecutorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fengbaojin
 * @date 2021/06/30
 */
@RestController
@RequestMapping(value = "/asyncExecutor")
@Api(description = "异步测试")
public class AsyncExecutorController {

    @Autowired
    private AsyncExecutorService ayncExecutorService;

    @GetMapping(value = "test")
    public ResponseResult<List<Integer>> test(HttpServletRequest request) throws InterruptedException {
        List<Integer> list = ayncExecutorService.test();
        return ResponseResult.success(list);
    }

}
