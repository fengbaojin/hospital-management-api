package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DrugNoteDO {

    private Long noteId;

    private Long drugId;

    private String drugName;

    private String describe;


    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdName;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;

    /**
     * 删除人ID
     */
    private Long deleterId;

    /**
     * 删除时间
     */
    private Date deletedDate;
}
