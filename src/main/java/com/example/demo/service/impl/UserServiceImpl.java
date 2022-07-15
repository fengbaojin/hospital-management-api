package com.example.demo.service.impl;

import com.example.demo.bo.AddMenuBO;
import com.example.demo.bo.AddUserBO;
import com.example.demo.bo.UpdateMenuBO;
import com.example.demo.bo.UserBO;
import com.example.demo.common.Constants;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.*;
import com.example.demo.exception.BusinessException;
import com.example.demo.manager.UserManager;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.utils.ToKenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Override
    public Pager<UserDO> getAllUser(Long departmentId, String name, Integer status, int pageIndex, int pageSize) {
        return userManager.getAllUser(departmentId, name, status, pageIndex, pageSize);
    }


    @Override
    public Boolean isLogin(String username, String password) {
        return userManager.isLogin(username, password);
    }

    @Override
    public UserBO login(String username, String password) {
        // 获取用户信息
        UserBO userBO = userManager.getLoginDO(username, password);
        // 获取token信息
        if (BeanUtils.isEmpty(userBO)) {
            throw new BusinessException(Constants.CODE_ERROR, "请检查用户密码是否正确");
        }
        String token = ToKenUtils.getToken(userBO.getUserId().toString(), password);
        userBO.setToken(token);
        return userBO;
    }

    @Override
    public List<RoleDO> getRoleList() {
        return userManager.getRoleList();
    }

    @Transactional
    @Override
    public ResponseResult<?> saveUser(Long userId, AddUserBO userBO) {
        UserDO userDO = UsersMapper.mapperToUserDO(userId, userBO);
        userManager.saveUser(userDO);
        return ResponseResult.success();
    }

    @Transactional
    @Override
    public ResponseResult<?> deleteUser(Long userId, Long deleteUserId) {
        userManager.deleteUser(userId, deleteUserId);
        return ResponseResult.success();
    }

    @Override
    public List<MenuDO> getMenuList() {
        return userManager.getMenuList();
    }

    @Override
    public Pager<RoleDO> getRoleListPage(int pageIndex, int pageSize) {
        return userManager.getRoleListPage(pageIndex, pageSize);
    }

    @Override
    public boolean getUserByUserName(String username) {
        return userManager.getUserByUserName(username);
    }

    @Override
    public List<MenuDO> getMenuListByRoleId(Long roleId) {
        return userManager.getMenuListByRoleId(roleId);
    }

    @Transactional
    @Override
    public void updateMenu(UpdateMenuBO updateMenuBO) {
        boolean isName = userManager.getRoleByName(updateMenuBO.getName());
        if (isName) {
            throw new BusinessException(Constants.CODE_ERROR, "角色名已存在");
        }
        if (BeanUtils.isEmpty(updateMenuBO.getRoleId())) {
            // 添加角色信息
            userManager.addRole(updateMenuBO);
        } else {
            // 修改角色信息
            userManager.updateRole(updateMenuBO);
        }
        // 删除角色菜单权限
        userManager.deleteRoleMenu(updateMenuBO.getRoleId());
        List<RoleMenuDO> roleMenuList = UsersMapper.mapperToRoleMenuDO(updateMenuBO.getRoleId(), updateMenuBO.getMenuIds());
        userManager.insertRoleMenu(roleMenuList);
    }

    @Override
    public Pager<MenuDO> getAllMenu(String name, int pageIndex, int pageSize) {
        return userManager.getAllMenu(name, pageIndex, pageSize);
    }

    @Override
    public List<MenuDO> getPidMenu() {
        return userManager.getPidMenu();
    }

    @Override
    public void addMenu(AddMenuBO addMenuBO) {
        if (BeanUtils.isEmpty(addMenuBO.getMenuId())) {
            userManager.addMenu(addMenuBO);
        }else {
            userManager.updateMenu(addMenuBO);
        }
    }
}
