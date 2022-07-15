package com.example.demo.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddSaleBO {

    private Integer drugId;

    private String drugName;

    private BigDecimal sellingPrice;

    private String comment;
}
