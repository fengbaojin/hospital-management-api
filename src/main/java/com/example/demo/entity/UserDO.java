package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author fengbaojin
 * 2021.12.21
 */
@Data
public class UserDO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 科室Id
     */
    private Integer departmentId;

    /**
     * 科室名称
     */
    private String departmentName;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 状态(0：在职中，1：休假中，3：离职中)
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdDate;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 修改人ID
     */
    private Long modifierId;

    /**
     * 修改时间
     */
    private Date modifiedDate;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 删除人ID
     */
    private Long deleterId;

    /**
     * 删除时间
     */
    private Date deletedDate;

}
