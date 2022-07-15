package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DrugDO {

    private Long drugId;

    private String drugName;

    private BigDecimal money;

    private String describe;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;

    private Integer quantity;

    private String companyName;


    private BigDecimal sellingPrice;

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
