package com.example.demo.controller;

import com.example.demo.common.ResponseResult;
import com.example.demo.mq.ImportExcelSender;
import com.example.demo.mq.receiver.ImportExcelReceiver;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author fengbaojin
 * @date 2021/10/28
 */
@RestController
@RequestMapping(value = "/sendMessage")
@Api(description = "测试rabbitmq分发消息")
public class SendMessAgeController {

    @Autowired
    private ImportExcelSender importExcelSender;

    @GetMapping(value = "/importExcel")
    public ResponseResult importExcel(HttpServletRequest request, String name) throws IOException {
        importExcelSender.sender(name);
        return ResponseResult.success();
    }

}
