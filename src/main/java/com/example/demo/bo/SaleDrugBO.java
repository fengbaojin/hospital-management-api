package com.example.demo.bo;

import lombok.Data;


/**
 * 出售药品BO
 */
@Data
public class SaleDrugBO {

    private Long drugId;

    private Integer quantity;

    private String comment;

}
