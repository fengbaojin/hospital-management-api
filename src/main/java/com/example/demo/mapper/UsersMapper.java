package com.example.demo.mapper;

import com.example.demo.bo.AddUserBO;
import com.example.demo.bo.UserBO;
import com.example.demo.entity.MenuDO;
import com.example.demo.entity.RoleMenuDO;
import com.example.demo.entity.UserDO;
import com.example.demo.utils.BeanUtils;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsersMapper {


    public static UserBO mapperToUserBO(UserDO userDO, List<MenuDO> menuDOList) {
        UserBO userBO = new UserBO();
        if (BeanUtils.isEmpty(userDO)) {
            return userBO;
        }
        userBO.setUserId(userDO.getUserId());
        userBO.setName(userDO.getName());

        // 该角色菜单的父级菜单
        if (BeanUtils.isNotEmpty(menuDOList)) {
            List<MenuDO> parentMenuDOList = menuDOList.stream().filter(p -> p.getPid() == null).collect(Collectors.toList());
            for (MenuDO parentMenuDO : parentMenuDOList) {
                List<MenuDO> subLevelMenuDOList = menuDOList.stream().filter(p -> Objects.equals(p.getPid(), parentMenuDO.getMenuId())).collect(Collectors.toList());
                parentMenuDO.setChildren(BeanUtils.isNotEmpty(subLevelMenuDOList) ? subLevelMenuDOList : null);
            }
            userBO.setMenus(parentMenuDOList);
        }

        return userBO;
    }

    public static UserDO mapperToUserDO(Long userId, AddUserBO userBO) {
        ModelMapper modelMapper = new ModelMapper();
        UserDO userDO = modelMapper.map(userBO, UserDO.class);

        if (BeanUtils.isEmpty(userBO.getUserId())) {
            userDO.setCreatedId(userId);
        } else {
            userDO.setModifierId(userId);
        }
        return userDO;
    }

    public static List<RoleMenuDO> mapperToRoleMenuDO(Long roleId, Long[] menuIds) {
        List<RoleMenuDO> list = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            list.add(roleMenuDO);
        }
        return list;
    }
}
