package com.example.demo.bo;

import com.example.demo.entity.MenuDO;
import lombok.Data;

import java.util.List;
@Data
public class UserBO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;

    private String avatarUrl;

    /**
     * token
     */
    private String token;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 菜单路由
     */
    private List<MenuDO> menus;
}
