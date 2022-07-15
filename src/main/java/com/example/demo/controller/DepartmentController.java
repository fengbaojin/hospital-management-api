package com.example.demo.controller;

import com.example.demo.common.ResponseResult;
import com.example.demo.entity.DepartmentDO;
import com.example.demo.service.DepartmentService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.utils.ToKenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/department")
@Api(description = "科室详情")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "getDepartmentList")
    @ApiOperation(value = "获取科室详情列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<List<DepartmentDO>> getDepartmentList(HttpServletRequest request) {

        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<DepartmentDO> departmentDOList =  departmentService.getDepartmentList();
        return ResponseResult.success(departmentDOList);
    }

}
