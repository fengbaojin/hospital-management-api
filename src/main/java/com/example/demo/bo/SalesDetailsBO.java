package com.example.demo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SalesDetailsBO {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    private String drugName;

    /**
     * 销售额
     */
    private BigDecimal salesVolume;

    /**
     * 销售数量
     */
    private Integer amount;

    /**
     * 盈利额
     */
    private BigDecimal profitNumber;

    public SalesDetailsBO(Date date, String drugName, BigDecimal salesVolume, Integer amount, BigDecimal profitNumber) {
        this.date = date;
        this.drugName = drugName;
        this.salesVolume = salesVolume;
        this.amount = amount;
        this.profitNumber = profitNumber;
    }

    public SalesDetailsBO(Date date) {
        this.date = date;
        this.drugName = "";
        this.salesVolume = BigDecimal.valueOf(0);
        this.amount = 0;
        this.profitNumber = BigDecimal.valueOf(0);
    }


    public SalesDetailsBO() {
        this.date = null;
        this.drugName = "";
        this.salesVolume = BigDecimal.valueOf(0);
        this.amount = 0;
        this.profitNumber = BigDecimal.valueOf(0);
    }

}
