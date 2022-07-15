package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库表记录DO
 */
@Data
public class DrugSaleDO {

    private Long saleId;

    private Long drugId;

    private String drugName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;

    private Integer quantity;

    private BigDecimal sellingPrice;

    private BigDecimal money;

    private BigDecimal priceDifference;

    private String comment;

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
