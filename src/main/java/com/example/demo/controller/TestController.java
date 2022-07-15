package com.example.demo.controller;

import com.example.demo.bo.DrugBO;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.Pager;
import com.example.demo.service.DrugInventoryService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.utils.ToKenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @author fengbaojin
 * @date 2021/10/28
 */
@RestController
@RequestMapping(value = "/user")
@Api(description = "用户")
public class TestController {

    @Autowired
    private DrugInventoryService drugInventoryService;

    @GetMapping(value = "/test")
    public ResponseResult<?> test(HttpServletRequest request) throws IOException {
        ResponseResult<?> test = drugInventoryService.test();
        return ResponseResult.success(test);
    }
}
