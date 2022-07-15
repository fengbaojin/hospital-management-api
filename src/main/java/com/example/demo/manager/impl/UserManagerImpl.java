package com.example.demo.manager.impl;

import com.example.demo.bo.AddMenuBO;
import com.example.demo.bo.UpdateMenuBO;
import com.example.demo.bo.UserBO;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.*;
import com.example.demo.manager.UserManager;
import com.example.demo.mapper.UsersMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.utils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao userDao;

    @Override
    public Pager<UserDO> getAllUser(Long departmentId, String name, Integer status, int pageIndex, int pageSize) {
        Pager<UserDO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<UserDO> list = userDao.getAllUser(departmentId, name, status);
            pager.setData(list);
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean isLogin(String username, String password) {
        return userDao.isLogin(username, password);
    }

    @Override
    public UserBO getLoginDO(String username, String password) {
        // 获取用户信息
        UserDO userDO = userDao.getUserByUsernameAndPassword(username, password);
        // 获取菜单信息
        List<MenuDO> menuDOList = userDao.getMenuList(BeanUtils.isEmpty(userDO) ? 0 : userDO.getRoleId());
        return UsersMapper.mapperToUserBO(userDO, menuDOList);
    }

    @Override
    public List<RoleDO> getRoleList() {
        return userDao.getRoleList();
    }

    @Transactional
    @Override
    public void saveUser(UserDO userDO) {
        if (BeanUtils.isEmpty(userDO.getUserId())) {
            userDao.saveUser(userDO);
        } else {
            userDao.updateUser(userDO);
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId, Long deleteUserId) {
        userDao.deleteUser(userId, deleteUserId);
    }

    @Override
    public List<MenuDO> getMenuList() {
        return userDao.getAllMenuList();
    }

    @Override
    public Pager<RoleDO> getRoleListPage(int pageIndex, int pageSize) {
        Pager<RoleDO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<RoleDO> list = userDao.getRoleList();
            pager.setData(list);
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getUserByUserName(String username) {
        return userDao.getUserByUserName(username);
    }

    @Override
    public List<MenuDO> getMenuListByRoleId(Long roleId) {
        return userDao.getMenuListByRoleId(roleId);
    }

    @Override
    public void updateRole(UpdateMenuBO updateMenuBO) {
        userDao.updateRole(updateMenuBO);
    }

    @Override
    public void deleteRoleMenu(Long roleId) {
        userDao.deleteRoleMenu(roleId);
    }

    @Override
    public void insertRoleMenu(List<RoleMenuDO> roleMenuList) {
        userDao.insertRoleMenu(roleMenuList);
    }

    @Override
    public void addRole(UpdateMenuBO updateMenuBO) {
        userDao.addRole(updateMenuBO);
    }

    @Override
    public boolean getRoleByName(String name) {
        return userDao.getRoleByName(name);
    }

    @Override
    public Pager<MenuDO> getAllMenu(String name, int pageIndex, int pageSize) {
        Pager<MenuDO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<MenuDO> list = userDao.getAllMenu(name);
            pager.setData(list);
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MenuDO> getPidMenu() {
        return userDao.getPidMenu();
    }

    @Override
    public void addMenu(AddMenuBO addMenuBO) {
        userDao.addMenu(addMenuBO);
    }

    @Override
    public void updateMenu(AddMenuBO addMenuBO) {
        userDao.updateMenu(addMenuBO);
    }
}
