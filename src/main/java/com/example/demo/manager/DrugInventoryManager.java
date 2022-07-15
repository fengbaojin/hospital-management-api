package com.example.demo.manager;

import com.example.demo.bo.*;
import com.example.demo.entity.*;

import java.util.Date;
import java.util.List;

public interface DrugInventoryManager {

    Pager<DrugBO> listDrugPage(String drugName, Integer startMoney, Integer endMoney, Date startProductionDate, Date endProductionDate, int pageIndex, int pageSize);

    DrugDO getDrugOne(Long drugId);

    void updateDrug(DrugDO drugDO);

    void insertDrugNoteDO(DrugNoteDO drugNoteDO);

    List<DrugNoteBO> getDrugNoteList(Long drugId);

    DrugDO getIsDrugOne(String drugName);

    Long insertDrug(DrugDO drug);

    void insertDrugRecord(DrugWareHouseDO record);

    Pager<DrugWareHouseDO> listDrugRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize);

    void insertDrugSale(DrugSaleDO drugSaleDO);

    Pager<DrugSaleDO> listDrugSaleRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize);

    SalesDetailsBO getTurnoverByLastThirtyDay();

    List<SalesDetailsBO> getTurnoverByEveryMonth(Date month);

    List<DrugBO> getDrugByName();

    Pager<SalesQuotaRankBO> getRankingByEveryMonth(Date startDate, Date endDate, int pageSize, int pageIndex, String orderBy);

    List<SalesDetailsBO> getSalesVolumeByName(Date month);

    List<SalesDetailsBO> getAmountByName(Date month);

    boolean updateSellingPrice(AddSaleBO addSaleBO);

    List<DrugBO> getNoSellingPrice();

    boolean batchSellingPrice(List<AddSaleBO> addSaleList);
}
