package com.example.demo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UpdateDrugBO {

    private Long noteId;

    private Long drugId;

    private String drugName;

    private String describe;


    /**
     * 创建人ID
     */
    private Long creatorId;

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
