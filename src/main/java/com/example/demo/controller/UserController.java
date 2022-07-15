package com.example.demo.controller;


import com.example.demo.bo.*;
import com.example.demo.common.Constants;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.MenuDO;
import com.example.demo.entity.Pager;
import com.example.demo.entity.RoleDO;
import com.example.demo.entity.UserDO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.utils.ToKenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author fengbaojin
 * @date 2021/10/28
 */
@RestController
@RequestMapping(value = "/user")
@Api(description = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "登录")
    public ResponseResult<UserBO> login(@RequestBody LoginBO loginBO) throws IOException {
        if (BeanUtils.isEmpty(loginBO)) {
            return ResponseResult.error("无效的登录信息", "无效的登录信息，请登录");
        }
        // 获取用户信息
        boolean isExist = userService.getUserByUserName(loginBO.getUsername());
        if (!isExist) {
            return ResponseResult.error("无效的登录信息", "用户名不存在");
        }
        Boolean count = userService.isLogin(loginBO.getUsername(), loginBO.getPassword());
        if (!count) {
            return ResponseResult.error("无效的登录信息", "用户名或密码错误");
        }
        UserBO userBO = userService.login(loginBO.getUsername(), loginBO.getPassword());
        return ResponseResult.success(userBO);
    }

    @GetMapping(value = "/getUserList")
    @ApiOperation(value = "获取人员列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"), @ApiImplicitParam(name = "departmentId", value = "科室ID", dataType = "Long", paramType = "query"), @ApiImplicitParam(name = "name", value = "名字或手机号", dataType = "String", paramType = "query"), @ApiImplicitParam(name = "status", value = "人员状态", dataType = "Integer", paramType = "query"), @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", paramType = "query"), @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query")})
    public ResponseResult<Pager<UserDO>> getAllUser(HttpServletRequest request,
                                                    Long departmentId,
                                                    String name,
                                                    Integer status,
                                                    @RequestParam(defaultValue = "1") int pageIndex,
                                                    @RequestParam(defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<UserDO> userList = userService.getAllUser(departmentId, name, status, pageIndex, pageSize);
        return ResponseResult.success(userList);
    }

    @GetMapping(value = "/getRoleListPage")
    @ApiOperation(value = "获取人员列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query")})
    public ResponseResult<Pager<RoleDO>> getRoleListPage(HttpServletRequest request,
                                                         @RequestParam(defaultValue = "1") int pageIndex,
                                                         @RequestParam(defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<RoleDO> roleDOPager = userService.getRoleListPage(pageIndex, pageSize);
        return ResponseResult.success(roleDOPager);
    }

    @GetMapping(value = "/getRoleList")
    @ApiOperation(value = "获取角色列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),})
    public ResponseResult<List<RoleDO>> getRoleList(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<RoleDO> userList = userService.getRoleList();
        return ResponseResult.success(userList);
    }

    @PostMapping(value = "/saveUser")
    @ApiOperation(value = "新增或修改用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),})
    public ResponseResult<?> saveUser(HttpServletRequest request, @RequestBody AddUserBO userBO) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(userBO)) {
            return ResponseResult.error("无效参数", "参数不能为空");
        }
        return userService.saveUser(userId, userBO);
    }

    @PostMapping(value = "/deleteUser")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),})
    public ResponseResult<?> deleteUser(HttpServletRequest request, Long deleteUserId) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(deleteUserId)) {
            return ResponseResult.error("无效参数", "参数不能为空");
        }
        return userService.deleteUser(userId, deleteUserId);
    }

    @GetMapping(value = "/getMenuList")
    @ApiOperation(value = "获取菜单列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")})
    public ResponseResult<List<MenuDO>> getMenuList(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<MenuDO> menuList = userService.getMenuList();
        return ResponseResult.success(menuList);
    }

    @GetMapping(value = "/getMenuListByRoleId")
    @ApiOperation(value = "获取菜单列表通过角色ID")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")})
    public ResponseResult<List<MenuDO>> getMenuListByRoleId(HttpServletRequest request, Long roleId) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<MenuDO> menuList = userService.getMenuListByRoleId(roleId);
        return ResponseResult.success(menuList);
    }

    @PostMapping(value = "/updateMenu")
    @ApiOperation(value = "修改菜单列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")})
    public ResponseResult updateMenu(HttpServletRequest request,
                                     @RequestBody UpdateMenuBO updateMenuBO) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        userService.updateMenu(updateMenuBO);
        return ResponseResult.success();
    }

    @GetMapping(value = "/getAllMenu")
    @ApiOperation(value = "获取所有菜单")
    public ResponseResult<Pager<MenuDO>> getAllMenu(HttpServletRequest request,
                                                    String name,
                                                    @RequestParam(defaultValue = "1") int pageIndex,
                                                    @RequestParam(defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<MenuDO> page = userService.getAllMenu(name, pageIndex, pageSize);
        return ResponseResult.success(page);
    }

    @GetMapping(value = "/getPidMenu")
    @ApiOperation(value = "获取所有菜单")
    public ResponseResult<List<MenuDO>> getPidMenu(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<MenuDO> page = userService.getPidMenu();
        return ResponseResult.success(page);
    }

    @PostMapping(value = "/addMenu")
    @ApiOperation(value = "添加菜单")
    public ResponseResult addMenu(HttpServletRequest request, @RequestBody AddMenuBO addMenuBO) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        userService.addMenu(addMenuBO);
        return ResponseResult.success();
    }


//    @GetMapping(value = "/findById")
//    public ResponseResult<User> findById(Long userId) {
//        User user = userMapper.selectById(userId);
//        return ResponseResult.success(user);
//    }
//
//    @GetMapping(value = "/findByList")
//    public ResponseResult<List<User>> findByList() {
//        List<User> user = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
//        return ResponseResult.success(user);
//    }
//
//    /**
//     * 分页查询
//     *
//     * @return
//     */
//    @GetMapping(value = "/findPage")
//    public ResponseResult<Long> findPage() {
//        Page<User> page = new Page<>(1, 5);
//        // 第一个参数page 第二个定义的条件
//        userMapper.selectPage(page, null);
//        Long total = page.getTotal();
//        return ResponseResult.success(total);
//    }
//
//
//    /**
//     * 分页查询
//     *
//     * @return
//     */
//    @GetMapping(value = "/deleteById")
//    public ResponseResult deleteById(Long userId) {
//        int count = userMapper.deleteById(userId);
//        return ResponseResult.success(count);
//    }


}
