package com.example.demo.entity;

import lombok.Data;

/**
 * 部门表
 */
@Data
public class DepartmentDO {

    /**
     * 科室id
     */
    private Long departmentId;

    /**
     * 科室名称
     */
    private String departmentName;
}
