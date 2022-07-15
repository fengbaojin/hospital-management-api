package com.example.demo.entity;

import lombok.Data;

/**
 * role表
 */
@Data
public class RoleDO {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色表述符
     */
    private String flag;
}
