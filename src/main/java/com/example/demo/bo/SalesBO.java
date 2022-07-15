package com.example.demo.bo;

import lombok.Data;

import java.util.List;
@Data
public class SalesBO {

    private List<SalesDetailsBO> salesVolumeList ;

    private List<SalesDetailsBO> amountList ;

    public SalesBO(List<SalesDetailsBO> salesVolumeList, List<SalesDetailsBO> amountList) {
        this.salesVolumeList = salesVolumeList;
        this.amountList = amountList;
    }


    public SalesBO() {
    }
}
