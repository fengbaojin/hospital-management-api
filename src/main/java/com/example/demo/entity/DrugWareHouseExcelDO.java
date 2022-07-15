package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DrugWareHouseExcelDO {

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "价格")
    private Integer money;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "生产日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;

    @ApiModelProperty(value = "生产公司")
    private String companyName;

    @ApiModelProperty(value = "描述")
    private String describe;
}
