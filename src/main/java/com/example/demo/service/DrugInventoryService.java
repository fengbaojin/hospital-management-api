package com.example.demo.service;

import com.example.demo.bo.*;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.DrugSaleDO;
import com.example.demo.entity.DrugWareHouseDO;
import com.example.demo.entity.DrugWareHouseExcelDO;
import com.example.demo.entity.Pager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DrugInventoryService {

    Pager<DrugBO> listDrugPage(String drugName, Integer startMoney, Integer endMoney, Date startProductionDate, Date endProductionDate, int pageIndex, int pageSize);

    ResponseResult<?> updateDrug(Long userId, DrugBO drugBO);

    List<DrugNoteBO> getDrugNoteList(Long drugId);

    ResponseResult<?> insetDrugWareHouse(Long userId, DrugWareHouseBO drugWareHouseBO);

    Pager<DrugWareHouseDO> listDrugRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize);

    ResponseResult<?> saleOfDrugs(Long userId, SaleDrugBO saleDrugBO);

    Pager<DrugSaleDO> listDrugSaleRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize);

    SalesDetailsBO getTurnoverByLastThirtyDay();

    List<SalesDetailsBO> getTurnoverByEveryMonth(Date month);

    List<DrugBO> getDrugByName();

    Pager<SalesQuotaRankBO> getRankingByEveryMonth(Date startDate, Date endDate, int pageSize, int pageIndex, String orderBy);

    List<SalesDetailsBO> getSalesVolumeByName(Date month);

    List<SalesDetailsBO> getAmountByName(Date month);

    SalesBO getSaleByName(Date month);

    ResponseResult<?> updateSellingPrice(AddSaleBO addSaleBO);

    List<DrugBO> getNoSellingPrice();

    ResponseResult<?> batchSellingPrice(List<AddSaleBO> addSaleList);

    ResponseResult<?> test();

    ResponseResult<?> importStore(Long userId, List<DrugWareHouseExcelDO> dtoList);

    ResponseResult<?> importStoreS(Long userId, MultipartFile file) throws IOException;
}
