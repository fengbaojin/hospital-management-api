package com.example.demo.service;

import com.example.demo.bo.AddMenuBO;
import com.example.demo.bo.AddUserBO;
import com.example.demo.bo.UpdateMenuBO;
import com.example.demo.bo.UserBO;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.MenuDO;
import com.example.demo.entity.Pager;
import com.example.demo.entity.RoleDO;
import com.example.demo.entity.UserDO;

import java.util.List;


public interface UserService {

    Pager<UserDO> getAllUser(Long departmentId, String name, Integer status, int pageIndex, int pageSize);

    /**
     * 获取登录信息
     *
     * @param username
     * @param password
     * @return
     */
    UserBO login(String username, String password);

    /**
     * 判断用户登录
     *
     * @param username
     * @param password
     * @return
     */
    Boolean isLogin(String username, String password);

    List<RoleDO> getRoleList();

    ResponseResult<?> saveUser(Long userId, AddUserBO userBO);

    ResponseResult<?> deleteUser(Long userId, Long deleteUserId);

    List<MenuDO> getMenuList();

    Pager<RoleDO> getRoleListPage(int pageIndex, int pageSize);

    boolean getUserByUserName(String username);

    List<MenuDO> getMenuListByRoleId(Long roleId);

    void updateMenu(UpdateMenuBO updateMenuBO);

    Pager<MenuDO> getAllMenu(String name, int pageIndex, int pageSize);

    List<MenuDO> getPidMenu();

    void addMenu(AddMenuBO addMenuBO);
}
