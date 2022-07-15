package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DrugWareHouseDO {

    private Long recordId;

    private Long drugId;

    private String drugName;

    private String describe;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;

    private Integer quantity;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;

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
