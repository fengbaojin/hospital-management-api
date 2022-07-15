package com.example.demo.manager;

import com.example.demo.bo.AddMenuBO;
import com.example.demo.bo.UpdateMenuBO;
import com.example.demo.bo.UserBO;
import com.example.demo.entity.*;

import java.util.List;

public interface UserManager {

    Boolean isLogin(String username, String password);

    UserBO getLoginDO(String username, String password);

    Pager<UserDO> getAllUser(Long departmentId, String name, Integer status, int pageIndex, int pageSize);

    List<RoleDO> getRoleList();

    void saveUser(UserDO userDO);

    void deleteUser(Long userId, Long deleteUserId);

    List<MenuDO> getMenuList();

    Pager<RoleDO> getRoleListPage(int pageIndex, int pageSize);

    boolean getUserByUserName(String username);

    List<MenuDO> getMenuListByRoleId(Long roleId);

    void updateRole(UpdateMenuBO updateMenuBO);

    void deleteRoleMenu(Long roleId);

    void insertRoleMenu(List<RoleMenuDO> roleMenuList);

    void addRole(UpdateMenuBO updateMenuBO);

    boolean getRoleByName(String name);

    Pager<MenuDO> getAllMenu(String name, int pageIndex, int pageSize);

    List<MenuDO> getPidMenu();

    void addMenu(AddMenuBO addMenuBO);

    void updateMenu(AddMenuBO addMenuBO);
}
