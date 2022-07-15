package com.example.demo.bo;

import lombok.Data;

@Data
public class AddUserBO {

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
     * 手机号
     */
    private String telephone;

    /**
     * 状态(1：在职中，2：休假中，3：离职中)
     */
    private Integer status;

    /**
     * 角色Id
     */
    private Long roleId;

}
