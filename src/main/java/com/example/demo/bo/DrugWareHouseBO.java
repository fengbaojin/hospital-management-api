package com.example.demo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库详情
 */
@Data
public class DrugWareHouseBO {

    private String drugName;

    private BigDecimal money;

    private String describe;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;

    private Integer quantity;

    private String companyName;
}
