package com.example.demo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DrugBO {

    private Long drugId;

    private String drugName;

    private BigDecimal money;

    private BigDecimal sellingPrice;

    private String describe;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;

    private Integer quantity;

   @Builder.Default
    private int saleQuantity = 0 ;




}
