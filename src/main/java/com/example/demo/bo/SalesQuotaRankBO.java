package com.example.demo.bo;

import lombok.Data;

@Data
public class SalesQuotaRankBO {

    private  String name;

    /**
     * 销售额
     */
    private double salesVolume;

    /**
     * 销售数量
     */
    private Integer amount;
}
