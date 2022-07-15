package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class DrugRecordDO {

    private Long recordId;

    private Long drugId;

    private String drugName;

    private Integer quantity;

    private String describe;

    /**
     * 创建人ID
     */
    private Long creatorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date CreatedDate;

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
