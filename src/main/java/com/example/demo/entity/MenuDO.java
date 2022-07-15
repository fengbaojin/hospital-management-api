package com.example.demo.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单表
 */
@Data
public class MenuDO {

    private Long menuId;

    private String name;

    private String path;

    private String icon;

    private Long pid;

    private String pidName;

    private String pagePath;

    private Integer sortNum;

    private List<MenuDO> children;

}
