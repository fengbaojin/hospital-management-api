package com.example.demo.controller;

import com.example.demo.common.ResponseResult;
import com.example.demo.service.ActivityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 * @author fengbaojin
 * @date 2021/06/30
 */
@RestController
@RequestMapping(value = "/activity")
@Api(description = "工作流")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping(value = "prepare")
    public ResponseResult prepare(HttpServletRequest request) throws InterruptedException {
        activityService.prepare();
        return ResponseResult.success();
    }

}
